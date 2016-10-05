package org.dmcs.transaction.analytics.speed.layer.adapters

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType, UserAccount, UserData}
import org.dmcs.transaction.analytics.speed.layer.spark.Spark
import org.dmcs.transaction.analytics.speed.layer.tests._
import org.scalatest.{ShouldMatchers, WordSpec}
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load
import prv.zielony.proper.inject._

/**
  * Created by Zielony on 2016-10-04.
  */
class AdaptersSpec extends WordSpec with ShouldMatchers with CashOperationsAdapter with ClientsAdapter
  with UserAccountsAdapter with Spark {

  implicit val batchProperties = load(classpath :/ "batch.properties")

  override val cashOperationsPath: String = %("batch.views.capital")
  override val clientsPath: String = %("batch.views.users")
  override val userAccountsPath: String = %("batch.views.accounts")

  "CashOperationsAdapter" should {

    val cashOperations = List(
      CashOperation(Timestamp.valueOf(LocalDateTime.now), 1, None, CashOperationType.Withdrawal, 12.0),
      CashOperation(Timestamp.valueOf(LocalDateTime.now), 2, None, CashOperationType.Insertion, 74.0),
      CashOperation(Timestamp.valueOf(LocalDateTime.now), 3, None, CashOperationType.Insertion, 28.0),
      CashOperation(Timestamp.valueOf(LocalDateTime.now), 2, Some(2), CashOperationType.Transfer, 33.0)
    )

    "provide all cash operations" in withSpark { implicit sparkContext => withSparkSql { implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(cashOperations.toDS, cashOperationsPath) { () =>
          withCashOperations { data =>

            val result = data.collect
            result should contain theSameElementsAs(cashOperations)
          }
        }
      }
    }
  }

  "ClientsAdapter" should {

    val clients = List(
      UserData(1, 12),
      UserData(2, 43),
      UserData(3, 77)
    )

    "provide all client data" in withSpark { implicit sparkContext => withSparkSql { implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(clients.toDS, clientsPath) { () =>
          withClientData { data =>
            val result = data.collect

            result should contain theSameElementsAs(clients)
          }
        }
      }
    }
  }

  "UserAccountsAdapter" should {

    val accounts = List(
      UserAccount(1, 1, 33.0, Some("UK"), Some(53)),
      UserAccount(2, 2, 48.0, Some("Poland"), None),
      UserAccount(3, 3, 72.0, None, None),
      UserAccount(4, 1, 59.0, Some("UK"), Some(53)),
      UserAccount(5, 4, 29.0, None, Some(44))
    )

    "provide all accounts" in withSpark { implicit sparkContext => withSparkSql { implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(accounts.toDS, userAccountsPath) { () =>
          withUserAccounts { data =>
            val result = data.collect
            result should contain theSameElementsAs(accounts)
          }
        }
      }
    }
  }
}