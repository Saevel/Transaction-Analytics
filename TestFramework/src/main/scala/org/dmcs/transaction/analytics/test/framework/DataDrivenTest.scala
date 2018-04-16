package org.dmcs.transaction.analytics.test.framework

import java.time.LocalDateTime

import org.dmcs.transaction.analytics.test.framework.components.generators.{GenerationTimeout, Generator}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.test.framework.utils._

import scala.concurrent.{ExecutionContext, Future}

trait DataDrivenTest[DataType, StatType] {

  protected val name: String

  protected val ingestor: Ingestor[DataType]

  protected val statistic: Statistic[DataType, StatType]

  protected val equality: Equality[StatType]

  protected def actualStatistic: () => Future[StatType]

  def execute(phaseId: Int, allData: Seq[DataType], phaseData: DataType)
             (implicit ex: ExecutionContext): Future[TestResult[StatType]] = {
      val startTimestamp = LocalDateTime.now
      ingestor
        .ingest(phaseData)
        .flatMap(_ => actualStatistic().map(actual => makeResult(statistic.evaluate(allData), actual, startTimestamp, phaseId)))
      }

  private[framework] def makeResult(expected: StatType,
                                    actual: StatType,
                                    startTimestamp: LocalDateTime,
                                    phaseId: Int): TestResult[StatType] =
    TestResult(name, phaseId, expected, actual, equality.areEqual(expected, actual), startTimestamp, LocalDateTime.now)
}