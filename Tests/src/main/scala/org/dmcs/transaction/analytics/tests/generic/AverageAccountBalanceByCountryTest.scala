package org.dmcs.transaction.analytics.tests.generic

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.{DoubleAverage, IntAverage}

import scala.concurrent.{ExecutionContext, Future}

class AverageAccountBalanceByCountryTest(kind: String,
                                         country: String,
                                         systemClient: SystemClient,
                                         override val equality: Equality[Double])
                                        (implicit actorSystem: ActorSystem,
                                         materializer: Materializer,
                                         ex: ExecutionContext) extends DataDrivenTest[ApplicationModel, Double]{
  override protected val name: String = s"$kind/AverageAccountBalanceByCountryTest"

  override protected val statistic: Statistic[ApplicationModel, Double] = Statistic.combine(
    {model => model.entitiesModel.userAccounts.filter(_.country == country).map(_.balance)},
    new DoubleAverage
  )

  override protected def actualStatistic: () => Future[Double] = () => systemClient.averageAccountBalanceFor(country)
}
