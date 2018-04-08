package org.dmcs.transaction.analytics.tests

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analytics.tests._
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.tests.generic._
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.ExecutionContext

object Tests {

  def apply(kind: String, countries: Seq[String], ingestor: Ingestor, systemClient: SystemClient)
           (implicit ex: ExecutionContext, actorSystem: ActorSystem, materializer: Materializer,
            doubleEquality: Equality[Double]): Seq[DataDrivenTest[ApplicationModel, _]] = Seq(
    new ClientsAgeMedianTest(kind, ingestor, systemClient),
    new AverageClientAgeTest(kind, ingestor, systemClient, doubleEquality),
    new ActiveAccountsByCountryTest(kind, countries.sample, ingestor, systemClient),
    new AverageAccountBalanceByCountryTest(kind, countries.sample, ingestor, systemClient, doubleEquality),
    new AverageInsertionInPeriodTest(kind, ingestor, systemClient, doubleEquality),
    new AverageWithdrawalInPeriodTest(kind, ingestor, systemClient, doubleEquality),
    new CapitalVariationInPeriodTest(kind, ingestor, systemClient, doubleEquality)
  )
}