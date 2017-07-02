package org.dmcs.transaction.analytics.lambda.speed.layer.actors

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.classical.rest.DefaultTimeout
import org.dmcs.transaction.analytics.lambda.speed.layer.spark.Spark
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CountAccountsByCountry, CountAccountsByCountryAndAgeInterval}
import org.dmcs.transaction.analytics.speed.layer.spark.Spark
import org.dmcs.transaction.analytics.speed.layer.tests._
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{ShouldMatchers, WordSpec}
import prv.zielony.proper.inject._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.duration._

class AccountsActorSpec extends WordSpec with ShouldMatchers with ScalaFutures with Spark with IntegrationPatience
  with DefaultTimeout {

  override implicit val defaultTimeout: Timeout = 3 minutes

  val batchProperties = load(classpath :/ "batch.properties")

  val accountsPath: String = %("batch.views.accounts" @@ batchProperties)

  val accounts = List(
    UserAccount(1, 1, 33.0, Some("UK"), Some(53)),
    UserAccount(2, 2, 48.0, Some("Poland"), None),
    UserAccount(3, 3, 72.0, None, None),
    UserAccount(4, 1, 59.0, Some("UK"), Some(53)),
    UserAccount(5, 4, 29.0, None, Some(44)),
    UserAccount(5, 4, 29.0, Some("UK"), Some(37))
  )

  "AccountsActor" should {

    "count accounts by country" in withActorSystem { implicit actorSystem => withSpark { implicit sparkContext =>
        withSparkSql {implicit sqlContext =>
          import sqlContext.implicits._
          withParquetData(accounts.toDS, accountsPath){() =>

            val accountsActor = actorSystem.actorOf(Props(new AccountsActor(accountsPath, sqlContext)), "AccountsActor")

            val (polandCount, ukCount) =
              (accountsActor ? CountAccountsByCountry("Poland"), accountsActor ? CountAccountsByCountry("UK"))

            polandCount.futureValue.toString.toInt should be(1)
            ukCount.futureValue.toString.toInt should be(3)
          }
        }
      }
    }

    "count accounts by country and age interval" in withActorSystem { implicit actorSystem => withSpark { implicit sparkContext =>
      withSparkSql { implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(accounts.toDS, accountsPath) { () =>

          val accountsActor = actorSystem.actorOf(Props(new AccountsActor(accountsPath, sqlContext)), "AccountsActor")

          val count = accountsActor ? CountAccountsByCountryAndAgeInterval("UK", Some(30), Some(40))

          count.futureValue.toString.toInt should be(1)
        }
      }
    }}
    //TODO: Ignore empty country & age values

    //TODO: Use only bottom age boundary when top one is not provided

    //TODO: Use only top age boundary when bottom one is not provided
  }
}