package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import java.sql.Timestamp
import java.time.LocalDateTime

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType}
import org.dmcs.transaction.analytics.lambda.batch.layer.tests._
import org.scalatest.{ShouldMatchers, WordSpec}

/**
  * Created by Zielony on 2016-10-02.
  */
class AccountEventAdapterSpec extends WordSpec with ShouldMatchers with AccountEventsAdapter with Spark {

  override val accountEventsPath: String = "target/MDS/Account"

  val data = List(
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 1, 1, 123.00, AccountEventType.Created),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 1, 1, 123.00, AccountEventType.Deleted),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 2, 2, 144.00, AccountEventType.Created),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 3, 3, 45.00, AccountEventType.Created),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 4, 4, 143.00, AccountEventType.Created),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 5, 5, 54.00, AccountEventType.Created),
    AccountEvent(Timestamp.valueOf(LocalDateTime.now), 5, 5, 92.00, AccountEventType.Deleted)
  )

  "AccountsEventAdapter" should {

    "provide all account events" in withSparkAndParquetData(data, accountEventsPath) { implicit sqlContext =>
      withAccountEvents { dataset =>
        val events = dataset.collect
        events should contain theSameElementsAs(data)
      }
    }

    "provide all 'Created' account events" in withSparkAndParquetData(data, accountEventsPath) { implicit sqlContext =>
      withAccountsCreated { dataset =>
        val events = dataset.collect
        events.foreach(event => event.kind should be(AccountEventType.Created))
        events should contain theSameElementsAs(data.filter(event => event.kind == AccountEventType.Created))
      }
    }

    "provide all 'Deleted' account events" in withSparkAndParquetData(data, accountEventsPath) { implicit sqlContext =>
      withAccountsDeleted { dataset =>
        val events = dataset.collect
        events.foreach(event => event.kind should be(AccountEventType.Deleted))
        events should contain theSameElementsAs(data.filter(event => event.kind == AccountEventType.Deleted))
      }
    }
  }

  private def withSparkAndParquetData[R](data: List[AccountEvent], path: String)(f: (SQLContext => R)): R =
    withSpark { implicit sparkContext =>
      withSparkSql { implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(data.toDS, path) { () =>
          f(sqlContext)
        }
      }
    }
}
