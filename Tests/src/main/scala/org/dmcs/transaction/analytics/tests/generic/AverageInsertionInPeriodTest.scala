package org.dmcs.transaction.analytics.tests.generic

import java.time.LocalDateTime

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.DoubleAverage

import scala.concurrent.{ExecutionContext, Future}

class AverageInsertionInPeriodTest(kind: String,
                                   systemClient: SystemClient,
                                   override val equality: Equality[Double])
                                  (implicit val system: ActorSystem, m: Materializer, ex: ExecutionContext) extends DataDrivenTest[ApplicationModel, Double] {

  override protected val name: String = s"$kind/AverageInsertionInPeriodTest"

  override protected val statistic: Statistic[ApplicationModel, Double] = Statistic.combine(
    {model => model.entitiesModel.operations.filter(_.kind == CashOperationType.Insertion).map(_.amount)}, new DoubleAverage)

  override protected def actualStatistic: () => Future[Double] = () => systemClient.averageInsertion()
}
