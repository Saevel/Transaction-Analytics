package org.dmcs.transaction.analytics.tests.generic

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.Count

import scala.concurrent.ExecutionContext

class ActiveAccountsByCountryTest(kind:String,
                                  country: String,
                                  systemClient: SystemClient)
                                 (implicit actorSystem: ActorSystem,
                                  materializer: Materializer,
                                  ex: ExecutionContext) extends DataDrivenTest[ApplicationModel, Long] {

  override protected val name = s"$kind/ActiveAccountsByCountryTest"

  override protected val statistic: Statistic[ApplicationModel, Long] = Statistic.combine(
    {model => model.entitiesModel.userAccounts.filter(_.country == country)},
    new Count
  )

  override protected val equality: Equality[Long] = Equality[Long]

  override protected def actualStatistic = () => systemClient.activeAccountsFor(country)
}
