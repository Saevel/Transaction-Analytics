package org.dmcs.transaction.analytics.tests.generic

import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.Count

class ActiveAccountsByCountryTest(kind:String,
                                  country: String,
                                  override val ingestor: Ingestor,
                                  systemClient: SystemClient) extends DataDrivenTest[ApplicationModel, Long] {

  override protected val name = s"$kind/ActiveAccountsByCountryTest"

  override protected val statistic: Statistic[ApplicationModel, Long] = Statistic.combine(
    {model => model.entitiesModel.userAccounts.filter(_.country == country)},
    Count
  )

  override protected val equality = Equality[Int]

  // TODO: Maybe make this depend on all data up to current phase? WHAT ABOUT THE STATISTIC?
  override protected def actualStatistic = () => systemClient.activeAccountsFor(country)
}
