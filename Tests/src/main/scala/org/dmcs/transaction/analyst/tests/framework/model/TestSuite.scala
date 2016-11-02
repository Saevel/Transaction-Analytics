package org.dmcs.transaction.analyst.tests.framework.model

import org.dmcs.transaction.analyst.tests._
import org.dmcs.transaction.analyst.tests.framework.generators.Generator
import org.dmcs.transaction.analyst.tests.framework.ingestors.Ingestor
import org.dmcs.transaction.analyst.tests.framework.loggers.{LogLevel, Logger}
import org.dmcs.transaction.analyst.tests.framework.matchers._
import org.dmcs.transaction.analyst.tests.framework.reporters.Reporter

import scala.concurrent.duration.FiniteDuration

/**
  * Created by Zielony on 2016-10-12.
  */
abstract class TestSuite[ModelType] extends Generator[ModelType] with Ingestor[ModelType] with Logger
  with Reporter {

  val phases: Int

  implicit val defaultLogLevel: LogLevel.Value = LogLevel.Debug

  private var modules: List[TestModule[ModelType]] = List.empty

  def run(implicit timeout: FiniteDuration): Unit = report(runPhases)

  private def runPhases(implicit timeout: FiniteDuration): Traversable[TestResult] = {

    val data: List[List[ModelType]] = (1 to phases)
      .toStream
      .map(i => List[ModelType](generate))
      .toList

    (1 to phases) flatMap { i =>
      log(s"\n\nRunning phase $i")
      val phaseData = generatePhaseData(data, i)
      log(s"Generated test data for phase $i")
      log("Ingesting data...")
      ingest(phaseData)
      log("Data ingestion done. Running tests now")
      modules.flatMap{ module =>
        module.tests.map { test =>
          log(s"\nExecuting: '${module.name} should ${test.name}' : Phase $i")
          val startTime = System.nanoTime();
          val matcher = test.method(phaseData)
          val testTime = System.nanoTime() - startTime;
          if(!matcher.matches) {
            log(s"Test failed: ${matcher.stringify}\n")
          } else {
            log("Test successful.\n")
          }
          TestResult(s"${module.name} should ${test.name}", i, matcher.matches, testTime)
        }
      }
    }
  }

  protected def module(name: String)(f: (TestModule[ModelType] => Unit)): Unit = {
    val m = TestModule[ModelType](name, List());
    modules = modules :+ m
    f(m)
  }

  protected def should(name: String)(f: (List[ModelType] => Matcher))(implicit module: TestModule[ModelType]): Unit = {
    module.tests = module.tests :+ Test(name, f)
  }

  private def generatePhaseData(data:List[List[ModelType]], phase: Int): List[ModelType] =
    (0 to (phase - 1)).flatMap(data(_)).toList

  protected implicit class DoubleComparisonRight(expected: Double) {
    def +-(precision: Double): (Double => DoubleMatcher) = DoubleMatcher(expected, _, precision)
  }

  protected implicit class DoubleComparison(actual: Double){
    def ~=(rightSide: (Double => DoubleMatcher)): DoubleMatcher = rightSide(actual)
  }

  protected implicit class FloatComparisonRight(expected: Float) {
    def +-(precision: Float): (Float => FloatMatcher) = FloatMatcher(expected, _, precision)
  }

  protected implicit class FloatComparison(actual: Float){
    def ~=(rightSide: (Float => FloatMatcher)): FloatMatcher = rightSide(actual)
  }

  protected implicit class LongComparison(actual: Long) {
    def ===(expected: Long):LongMatcher = LongMatcher(expected, actual)
  }

  protected implicit class IntComparison(actual: Int) {
    def ===(expected: Int):IntMatcher = IntMatcher(expected, actual)
  }

  protected implicit class AndCondition(first: Matcher) {
    def and(second: Matcher): AndMatcher = AndMatcher(first, second)
  }

  protected implicit class OrCondition(first: Matcher) {
    def or(second: Matcher): OrMatcher = OrMatcher(first, second)
  }
}