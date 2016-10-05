package org.dmcs.transaction.analytics.speed.layer.actors

import java.sql.Timestamp
import java.time.LocalDateTime

import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analyst.lambda.model.CashOperationType._
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CapitalChangeInPeriod, AverageInsertionInPeriod, AverageWithdrawalInPeriod}
import org.dmcs.transaction.analytics.speed.layer.rest.DefaultTimeout
import org.dmcs.transaction.analytics.speed.layer.spark.Spark
import org.dmcs.transaction.analytics.speed.layer.tests._
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.time.{Minutes, Span}
import org.scalatest.{ShouldMatchers, WordSpec}
import prv.zielony.proper.inject._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._

class CapitalActorSpec extends WordSpec with ShouldMatchers with Spark with ScalaFutures with DefaultTimeout {

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(Span(3, Minutes))

  val batchProperties = load(classpath :/ "batch.properties")

  val cashOperationsPath = %("batch.views.capital" @@ batchProperties)

  override implicit val defaultTimeout: Timeout = 3 minutes

  val now = LocalDateTime.now();

  val cashOperations = List(
    CashOperation(Timestamp.valueOf(now.minusDays(7)), 1, None, Insertion, 10.0),
    CashOperation(Timestamp.valueOf(now.minusDays(3)), 1, None, Insertion, 120.0),
    CashOperation(Timestamp.valueOf(now.minusDays(1)), 1, None, Withdrawal, 20.0),
    CashOperation(Timestamp.valueOf(now.minusDays(5)), 2, None, Insertion, 40.0),
    CashOperation(Timestamp.valueOf(now.minusDays(2)), 1, None, Withdrawal, 20.0),
    CashOperation(Timestamp.valueOf(now.minusDays(1)), 1, Some(2), Transfer, 10.0),
    CashOperation(Timestamp.valueOf(now), 2, Some(1), Transfer, 30.0)
  )

  "CapitalActor" should {

    "calculate average insertion" in withSpark{ implicit sparkContext => withSparkSql { implicit sqlContext =>
        withActorSystem { implicit actorSystem =>
          import sqlContext.implicits._
          withParquetData(cashOperations.toDS, cashOperationsPath) { () =>

            val accountsActor = actorSystem.actorOf(Props(new CapitalActor(cashOperationsPath, sqlContext)))
            val averageInsertion = (accountsActor ? AverageInsertionInPeriod(None, None)).map(_.toString.toDouble)

            averageInsertion.futureValue should be(totalAverage(CashOperationType.Insertion) +- 0.01)
          }
        }
      }
    }

    "calculate average withdrawal" in withSpark{ implicit sparkContext => withSparkSql { implicit sqlContext =>
      withActorSystem { implicit actorSystem =>
        import sqlContext.implicits._
        withParquetData(cashOperations.toDS, cashOperationsPath) { () =>
            val accountsActor = actorSystem.actorOf(Props(new CapitalActor(cashOperationsPath, sqlContext)))
            val averageWithdrawal = (accountsActor ? AverageWithdrawalInPeriod(None, None)).map(_.toString.toDouble)

            averageWithdrawal.futureValue should be(totalAverage(CashOperationType.Withdrawal) +- 0.01)
          }
        }
      }
    }

    "calculate capital variance" in withSpark{ implicit sparkContext => withSparkSql { implicit sqlContext =>
      withActorSystem { implicit actorSystem =>
        import sqlContext.implicits._
        withParquetData(cashOperations.toDS, cashOperationsPath) { () =>
            val accountsActor = actorSystem.actorOf(Props(new CapitalActor(cashOperationsPath, sqlContext)))
            val capitalChange = (accountsActor ? CapitalChangeInPeriod(None, None)).map(_.toString.toDouble)

            capitalChange.futureValue should be(expectedCapitalChange +- 0.01)
          }
        }
      }
    }
  }

  private def totalAverage(kind: CashOperationType): Double =
    cashOperations.filter(_.kind == kind).map(_.amount).avg

  private def expectedCapitalChange: Double = cashOperations.map {
      case insertion @ CashOperation(_, _, _, Insertion, amount) => amount
      case withdrawal @ CashOperation(_, _, _, Withdrawal, amount) => (-1) * amount
      case _ => 0.0
    } reduce(_ + _)
}