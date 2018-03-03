package org.dmcs.transaction.analytics.tests.framework.model

import org.dmcs.transaction.analytics.tests.framework.converters.Converter

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration
import scala.util.{Failure, Success, Try}
import com.typesafe.scalalogging.LazyLogging
import org.dmcs.transaction.analytics.tests.framework.data.DataFormat
import org.dmcs.transaction.analytics.tests.framework.evaluators.Evaluator
import org.dmcs.transaction.analytics.tests.framework.generators.Generator
import org.dmcs.transaction.analytics.tests.framework.ingestors.{Ingestor, SinkType}

abstract class Test[DataType, StatisticType, Sink <: SinkType, FormatDataType, Format <: DataFormat[FormatDataType]](val name: String, val iterations: Int, val maxSize: Int)
  extends LazyLogging { self: (Generator[DataType] with Evaluator[DataType, StatisticType] with Ingestor[Sink, FormatDataType, Format]) =>

  protected implicit val converter: Converter[DataType, FormatDataType, Format]

  protected implicit val testTimeout: FiniteDuration

  protected val equality: Equallity[StatisticType]

  protected def test(data: Traversable[DataType]): Future[StatisticType]

  def run(implicit context: ExecutionContext): Stream[TestResult[DataType]] = {

    val iterationData = Stream.fill(iterations)(Stream.fill(maxSize)(generate).filter(_.isSuccess).map(_.get))

    logger.debug(s"Generated test data for $iterations phases, each of maximum size of $maxSize")

    val expectedResults = iterationData.map(evaluate)

    logger.debug("Evaluated expected statistics values")

    val futureResults: Stream[Future[StatisticType]] = iterationData.zipWithIndex.map { case (data, phase) =>

      val ingestionComplete = ingest(data.map(converter.convert))

      ingestionComplete.onComplete{
        case Success(_) => logger.info(s"Succeeded to ingest data for phase: ${phase + 1}")
        case Failure(e) => logger.error(s"Failed to ingest data for phase: ${phase + 1}", e)
      }

      val bodyComplete = ingestionComplete.flatMap(_ => test(data))

      bodyComplete.onComplete {
        case Success(_) => logger.info(s"Succeeded to execute test body for phase: ${phase + 1}")
        case Failure(e) => logger.error(s"Failed to execute test body for phase: ${phase + 1}")
      }

      bodyComplete
    }

    val comparisons = futureResults.zip(expectedResults).map{ case (futureResult, expectedResult) =>
      futureResult.map(result => (result, expectedResult))
    }

    val results = comparisons.map(future => Try(Await.result(future, testTimeout))).zip(iterationData) map { case (attempt, data) =>

      attempt match {

        case Success((actual, expected)) if(equality.areEqual(actual, expected)) =>
          TestSuccess[DataType, StatisticType](data, actual, expected)

        case Success((actual, expected)) => TestFailure[DataType, StatisticType](data, actual, expected)

        case failure @ Failure(e) => TestError[DataType, StatisticType](data, failure)
      }
    }

    // TODO: Incorporate this better into test lifecycle.
    Await.result(cleanup, testTimeout)

    results
  }
}