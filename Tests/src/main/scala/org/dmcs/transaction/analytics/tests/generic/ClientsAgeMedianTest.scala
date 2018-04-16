package org.dmcs.transaction.analytics.tests.generic

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ResponseEntity
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.Materializer
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.Median

import scala.concurrent.{ExecutionContext, Future}

class ClientsAgeMedianTest(kind: String, systemClient: SystemClient)
                          (implicit actorSystem: ActorSystem, materializer: Materializer, ex: ExecutionContext)
  extends DataDrivenTest[ApplicationModel, Int] {

  override protected val name = s"$kind/ClientsAgeMedianTest"

  override protected val statistic: Statistic[ApplicationModel, Int] = Statistic.combine[ApplicationModel, Int, Int](
    {model => model.entitiesModel.userData.map(_.age)},
    Median
  )

  override protected val equality = Equality[Int]

  override protected def actualStatistic: (() => Future[Int]) = () => systemClient.clientsAgeMedian
}
