package org.dmcs.transaction.analytics.tests.generic

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.dmcs.transaction.analytics.test.framework.{DataDrivenTest, Equality}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic
import org.dmcs.transaction.analytics.tests.http.SystemClient
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.dmcs.transaction.analytics.tests.statistics.IntAverage

import scala.concurrent.{ExecutionContext, Future}

class AverageClientAgeTest(kind: String,
                           systemClient: SystemClient,
                           override val equality: Equality[Double])
                          (implicit actorSystem: ActorSystem, materializer: Materializer, ex: ExecutionContext)
  extends DataDrivenTest[ApplicationModel, Double]{

  override protected val name = s"$kind/AverageClientAgeTest"

  override protected val statistic = Statistic.combine[ApplicationModel, Int, Double](
    {model => model.entitiesModel.userData.map(_.age)},
    new IntAverage
  )

  override protected def actualStatistic: () => Future[Double] = () => systemClient.userAgeAverage
}