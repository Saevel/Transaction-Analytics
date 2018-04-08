package org.dmcs.transaction.analytics.tests

import java.io.File
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analytics.test.framework.components.generators.Generator
import org.dmcs.transaction.analytics.test.framework.components.ingestors.{IdleIngestor, Ingestor}
import org.dmcs.transaction.analytics.test.framework.components.reporters.CsvReporter
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality, TestRunner}
import org.dmcs.transaction.analytics.tests.generators.ApplicationModelGenerator
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import prv.zielony.proper.inject._
import prv.zielony.proper.converters._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

object AcceptanceTestsApplication extends App {

  implicit val formatter = DateTimeFormatter.ISO_DATE_TIME

  // TODO: Command line param deciding between test-classical.properties and test-lambda.properties on the classpath?
  implicit val bundle: Bundle = load(classpath :/ "application.properties")

  private val configuration: AcceptanceTestConfiguration = AcceptanceTestConfiguration()

  private val ex = implicitly[ExecutionContext]

  private val generator: Generator[ApplicationModel] = ApplicationModelGenerator(configuration.generators)

  private val reporter = new CsvReporter(new File(configuration.outputFile))

  private implicit val doubleEquality: Equality[Double] = Equality[Double]((x, y) => Math.abs(x - y) < configuration.doublePrecision)

  // TODO: Implement so depending on config it's either HDFS or Cassandra!
  private val ingestor: Ingestor[Dataset] = if(configuration.kind == TestKind.Classical) {
    ???
  } else {
    ???
  }

  private val systemClient: SystemClient = new SystemClient(configuration.http.host, configuration.http.port)

  private val tests: Seq[DataDrivenTest[ApplicationModel, _]] = Tests(configuration.kind.toString, ingestor, systemClient)

  private val testRunner: TestRunner[ApplicationModel] = new TestRunner[ApplicationModel](reporter, generator, configuration.phaseCount)

  Try(Await.result(testRunner.runAll(tests)(ex, configuration.generators.generationTimeouts), configuration.testTimeout)) match {
    case Success(_) => println("All tests run successfully")
    case Failure(e) => {
      println("Failure while running tests")
      e.printStackTrace
    }
  }
}