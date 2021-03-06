package org.dmcs.transaction.analytics.tests

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.dmcs.transaction.analytics.test.framework.components.generators.Generator
import org.dmcs.transaction.analytics.test.framework.components.ingestors.{IdleIngestor, Ingestor}
import org.dmcs.transaction.analytics.test.framework.components.reporters.CsvReporter
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality, TestRunner}
import org.dmcs.transaction.analytics.tests.generators.ApplicationModelGenerator
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.ingestors.{CassandraIngestor, CsvIngestor, HDFSIngestor}
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

object AcceptanceTestsApplication extends App {

  implicit val formatter = DateTimeFormatter.ISO_DATE_TIME

  implicit val bundle: Bundle = load(classpath :/ "application.properties")

  private val configuration: AcceptanceTestConfiguration = AcceptanceTestConfiguration(
    TestKind.withName(System.getenv("TEST_KIND")),
    System.getenv("HTTP_HOST"),
    System.getenv("HTTP_PORT").toInt
  )

  private val ex = implicitly[ExecutionContext]

  private val generator: Generator[ApplicationModel] = ApplicationModelGenerator(configuration.generators)

  private val reporter = new CsvReporter(
    new File(s"/usr/reports/${configuration.tests.kind}/${LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"))}/${configuration.tests.outputFile}")
  )

  private implicit val session: SparkSession = SparkSession.builder.
    appName(s"AcceptanceTests${configuration.tests.kind}")
    .master(System.getenv("SPARK_MASTER"))
    .config("spark.cassandra.connection.host", configuration.ingestion.cassandra.host)
    .config("spark.cassandra.connection.port", configuration.ingestion.cassandra.port.toString)
    .config("spark.cassandra.auth.username", configuration.ingestion.cassandra.username)
    .config("spark.cassandra.auth.password", configuration.ingestion.cassandra.password)
    .getOrCreate

  private implicit val context: SparkContext = session.sparkContext

  private implicit val sqlContext: SQLContext = session.sqlContext

  private implicit val actorSystem: ActorSystem = ActorSystem(s"AcceptanceTests${configuration.tests.kind}")

  private implicit val materialier: Materializer = ActorMaterializer()

  private implicit val doubleEquality: Equality[Double] = Equality[Double]((x: Double, y: Double) => Math.abs(x - y) < configuration.tests.doublePrecision)

  private val ingestor: Ingestor[ApplicationModel] = if(configuration.tests.kind == TestKind.Classical) {
    CassandraIngestor(configuration.ingestion.cassandra)
  } else {
    HDFSIngestor(configuration.ingestion.hdfs)
  }

  // private val ingestor: Ingestor[ApplicationModel] = CsvIngestor(s"data/${LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"))}")

  private val systemClient: SystemClient = new SystemClient(configuration.http.host, configuration.http.port)

  private val tests: Seq[DataDrivenTest[ApplicationModel, _]] =
    Tests(configuration.tests.kind.toString, configuration.generators.users.countries, ingestor, systemClient)

  private val testRunner: TestRunner[ApplicationModel] =
    new TestRunner[ApplicationModel](reporter, generator, ingestor, configuration.tests.phaseCount)

  Try(Await.result(testRunner.runAll(tests)(ex, configuration.generators.generationTimeouts), configuration.tests.testTimeout)) match {
    case Success(_) => {
      println("All tests run successfully")
      System.exit(0)
    }
    case Failure(e) => {
      println("Failure while running tests")
      e.printStackTrace
      System.exit(1)
    }
  }
}