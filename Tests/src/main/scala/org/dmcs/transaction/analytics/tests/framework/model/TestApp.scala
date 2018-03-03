package org.dmcs.transaction.analytics.tests.framework.model

import com.typesafe.scalalogging.LazyLogging
import org.dmcs.transaction.analytics.tests.framework.data.DataFormat
import org.dmcs.transaction.analytics.tests.framework.ingestors.SinkType
import org.dmcs.transaction.analytics.tests.framework.reporters.Reporter

import scala.concurrent.ExecutionContext

trait TestApp extends App with LazyLogging { self: Reporter =>

  protected def execute[DataType, StatisticType, Sink <: SinkType, FormatDataType, Format <: DataFormat[FormatDataType]](test: Test[DataType, StatisticType, Sink, FormatDataType, Format])
                                                                                                                        (implicit context: ExecutionContext): Unit = {
    val results = test.run

    logger.info(s"${test.name}. Iterations: ${test.iterations}. Size: ${test.maxSize}.")

    report(results)

    results.zipWithIndex.foreach {
      case (TestSuccess(data, actual, expected), i: Int) =>
        logger.info(s"Iteration: ${i + 1}. Expected Value: $expected. Actual Value: $actual. Result: SUCCESS")

      case (TestFailure(data, actual, expected), i: Int) =>
        logger.warn(s"Iteration: ${i + 1}. Expected Value: $expected. Actual Value: $actual. Result: FAILURE")

      case (TestError(data, message, cause), i: Int) => {
        logger.error(s"Iteration: ${i + 1}. Result: ERROR. Message: $message.", cause)
      }
    }
  }
}
