package org.dmcs.transaction.analytics.test.framework.components.reporters

import java.io.{File, FileWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.dmcs.transaction.analytics.test.framework.TestResult

import scala.concurrent.ExecutionContext.Implicits.global
import org.dmcs.transaction.analytics.test.framework.utils.FileIO
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.PropertyChecks
import org.scalacheck.Arbitrary._
import org.scalacheck.{Gen, Test}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

import scala.io.Source

@RunWith(classOf[JUnitRunner])
class CsvReporterTest extends WordSpec with PropertyChecks with Matchers with FileIO with ScalaFutures
  with IntegrationPatience {

  private val targetFile = new File("build/results.csv")

  private val reporter = new CsvReporter(targetFile)

  private implicit val parameters = Test.Parameters.default.withMinSuccessfulTests(10)

  private val testResults = Gen.choose(1, 10).flatMap(n => Gen.listOfN(n, for {
    expected <- arbitrary[Double]
    phaseId <- arbitrary[Long]
    passed <- arbitrary[Boolean]
    delta <- arbitrary[Double]
    actual <- if(passed) Gen.choose(expected - delta, expected + delta) else Gen.choose(expected - 3 * delta, expected - 2 * delta)
  } yield TestResult("sampleTest", phaseId, expected, actual, delta, passed, LocalDateTime.now, LocalDateTime.now)
  ))

  "CsvReporter" should {

    "report results to a CSV file" in forAll(testResults){ results =>
      withEmptyFile(targetFile.getPath){ file =>

        reporter.report(results).futureValue

        Source.fromFile(file)
          .getLines()
          .map(_.split(","))
          .map(elements =>
            TestResult[Double](elements(0), elements(1).toLong, elements(2).toDouble, elements(3).toDouble, elements(4).toDouble,
              elements(5).toBoolean, LocalDateTime.parse(elements(6), DateTimeFormatter.ISO_DATE_TIME),
              LocalDateTime.parse(elements(7), DateTimeFormatter.ISO_DATE_TIME))
          ).toSeq should contain theSameElementsAs(results)
      }
    }
  }
}
