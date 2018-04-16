package org.dmcs.transaction.analytics.tests.generic

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analyst.lambda.model.CashOperationType
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.DoubleAverage

import scala.concurrent.{ExecutionContext, Future}

class AverageWithdrawalInPeriodTest(kind: String,
                                    override val ingestor: Ingestor[ApplicationModel],
                                    systemClient: SystemClient,
                                    override val equality: Equality[Double])
                                   (implicit system: ActorSystem, m: Materializer, ex: ExecutionContext) extends DataDrivenTest[ApplicationModel, Double] {

  override protected val name: String = s"$kind/AverageWithdrawalInPeriodTest"

  override protected val statistic: Statistic[ApplicationModel, Double] = Statistic.combine(
    {model => model.entitiesModel.operations.filter(_.kind == CashOperationType.Withdrawal).map(_.amount)}, new DoubleAverage)

  override protected def actualStatistic: () => Future[Double] = () => systemClient.averageWithdrawal()
}
