package org.dmcs.transaction.analytics.test

import org.dmcs.transaction.analytics.test.evaluators.Evaluator
import org.dmcs.transaction.analytics.test.filters.Filter
import org.dmcs.transaction.analytics.test.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.processors.Processor
import org.scalacheck.Gen

import scala.concurrent.Future

/**
  * Created by Zielony on 2016-08-07.
  */
package object dsl {
  /*
  val client:TransactionAnalystClient = ???

  val sampleGen:Gen[Traversable[UserEvent]] = ???

  val countryFilter: Filter[UserEvent] = ???

  val average: Processor[UserEvent, Double] = ???

  val toHadoop: Ingestor[UserEvent] = ???

  val testEvaluator: Evaluator[Double] = ???

  val sampleTest: TestConfig[UserEvent, Double] = sampleGen
    .filterWith(event => true)
    .process(events => 1.0)
    .ingest(events => {})
    .check(client.clientAgeAverage)
    .evaluate((actual, expected) => 1.0)

  val otherTest: TestConfig[UserEvent, Double] =
    sampleGen filterWith countryFilter process average ingest toHadoop check client.clientAgeAverage evaluate testEvaluator
  */

  implicit class GenerationConfig[DataType](val generator: Gen[Traversable[DataType]]) {

    def filterUsing(f: Filter[DataType]*): FilterConfig[DataType] = FilterConfig[DataType](generator, f);

    def filterWith(f: (DataType => Boolean)*): FilterConfig[DataType] = {
      val filter = f map { lambda =>
        new Filter[DataType] {
          override def filter(data: DataType): Boolean = lambda(data)
        }
      }
      FilterConfig(generator, filter)
    }
  }

  case class FilterConfig[DataType](generator: Gen[Traversable[DataType]], filters: Seq[Filter[DataType]]) {

    def process[StatisticType](processor: Processor[DataType, StatisticType]): ProcessorConfig[DataType, StatisticType] =
      ProcessorConfig[DataType, StatisticType](generator, filters, processor)

    def process[StatisticType](processing: (Traversable[DataType] => StatisticType)): ProcessorConfig[DataType, StatisticType] = {
      val processor = new Processor[DataType, StatisticType] {
        override def process(input: Traversable[DataType]): StatisticType = processing(input)
      }

      ProcessorConfig[DataType, StatisticType](generator, filters, processor)
    }
  }

  case class ProcessorConfig[DataType, StatisticType](generator: Gen[Traversable[DataType]],
                                                      filters: Seq[Filter[DataType]],
                                                      processors: Processor[DataType, StatisticType]) {

    def ingest(ingestor: Ingestor[DataType]): IngestorConfig[DataType, StatisticType] =
      IngestorConfig(generator, filters, processors, ingestor)

    def ingest(ingestion: (Traversable[DataType] => Unit)) = {
      val ingestor = new Ingestor[DataType] {
        override def ingest(data: Traversable[DataType]): Unit = ingest(data)
      }

      IngestorConfig(generator, filters, processors, ingestor)
    }
  }

  case class IngestorConfig[DataType, StatisticType](generator: Gen[Traversable[DataType]],
                                                     filters: Seq[Filter[DataType]],
                                                     processor: Processor[DataType, StatisticType],
                                                     ingestor: Ingestor[DataType]) {

    def check(checker: => Future[Either[Throwable, StatisticType]]): CheckConfig[DataType, StatisticType] =
      new CheckConfig[DataType, StatisticType](generator, filters, processor, ingestor, checker)
  }

  class CheckConfig[DataType, StatisticType](val generator: Gen[Traversable[DataType]],
                                             val filters: Seq[Filter[DataType]],
                                             val processor: Processor[DataType, StatisticType],
                                             val ingestor: Ingestor[DataType],
                                             check: => Future[Either[Throwable, StatisticType]]) {

    def evaluate(evaluator: Evaluator[StatisticType]): TestConfig[DataType, StatisticType] =
      new TestConfig(generator, filters, processor, ingestor, check, evaluator)

    def evaluate(eval: ((StatisticType, Either[Throwable, StatisticType]) => Double)): TestConfig[DataType, StatisticType] = {
      val evaluator = new Evaluator[StatisticType] {
        override def evaluate(expected: StatisticType, actual: Either[Throwable, StatisticType]): Double =
          eval(expected, actual)
      }

      new TestConfig(generator, filters, processor, ingestor, check, evaluator)
    }
  }

  class TestConfig[DataType, StatisticType](val generator: Gen[Traversable[DataType]],
                                            val filters: Seq[Filter[DataType]],
                                            val processor: Processor[DataType, StatisticType],
                                            val ingestor: Ingestor[DataType],
                                            check: => Future[Either[Throwable, StatisticType]],
                                            val evaluator: Evaluator[StatisticType]) {
    def checkResult: Future[Either[Throwable, StatisticType]] = check
  }
}