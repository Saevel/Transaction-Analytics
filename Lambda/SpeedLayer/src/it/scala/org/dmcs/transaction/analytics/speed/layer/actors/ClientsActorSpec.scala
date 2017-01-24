package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.olap.rest.DefaultTimeout
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}
import org.dmcs.transaction.analytics.speed.layer.spark.Spark
import org.dmcs.transaction.analytics.speed.layer.tests._
import org.scalatest.{ShouldMatchers, WordSpec}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import prv.zielony.proper.inject._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._

class ClientsActorSpec extends WordSpec with ShouldMatchers with ScalaFutures with IntegrationPatience with Spark
  with DefaultTimeout {

  val batchProperties = load(classpath :/ "batch.properties")

  val usersPath = %("batch.views.users" @@ batchProperties)

  override implicit val defaultTimeout: Timeout = 3 minutes

  val users = List(
    UserData(1, 35),
    UserData(2, 40),
    UserData(3, 30),
    UserData(4, 25),
    UserData(5, 20)
  )

  "ClientsActor" should {

    "calculate average user age" in withSpark { implicit sparkContext => withSparkSql { implicit sqlContext =>
      withActorSystem { implicit actorSystem =>
        import sqlContext.implicits._
        withParquetData(users.toDS, usersPath) { () =>

          val clientsActor = actorSystem.actorOf(Props(new ClientsActor(usersPath, sqlContext)))

          val averageAge = (clientsActor ? UserAgeAverage) map(_.toString.toDouble)

          averageAge.futureValue should be(users.map(_.age).avg +- 0.01)
        }
      }
    }}

    "calculate user age median" in withSpark { implicit sparkContext => withSparkSql { implicit sqlContext =>
      withActorSystem { implicit actorSystem =>
        import sqlContext.implicits._
        withParquetData(users.toDS, usersPath) { () =>

          val clientsActor = actorSystem.actorOf(Props(new ClientsActor(usersPath, sqlContext)))

          val ageMedian = (clientsActor ? UserAgeMedian) map(_.toString.toDouble)

          ageMedian.futureValue should be(30.0 +- 0.01)
        }
      }
    }}
  }
}
