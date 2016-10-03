package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import java.sql.Timestamp
import java.time.LocalDateTime

import org.apache.spark.sql.{Dataset, SQLContext}

import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.batch.layer.tests._
import org.dmcs.transaction.analytics.lambda.events.{TransactionEvent, TransactionEventType, UserEventType}

import org.scalatest.{ShouldMatchers, WordSpec}

class TransactionEventsAdapterSpec extends WordSpec with ShouldMatchers with TransactionEventsAdapter with Spark {

  val data = List(
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 1, Some(2), 12.5, TransactionEventType.Transfer),
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 1, None, 100.50, TransactionEventType.Withdrawal),
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 2, None, 12.5, TransactionEventType.Insertion),
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 2, Some(3), 77.0, TransactionEventType.Transfer),
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 3, None, 53.50, TransactionEventType.Withdrawal),
    TransactionEvent(Timestamp.valueOf(LocalDateTime.now()), 4, None, 24.50, TransactionEventType.Insertion)
  )

  override val transactionEventsPath: String = "target/MDS/Transactions"

  "TransactionEventsAdapter" should {

    "provide all transaction events" in withSparkAndParquetData(data, transactionEventsPath) { implicit sqlContext =>
      withTransactionEvents { dataset =>
        val events = dataset.collect
        events should contain theSameElementsAs (data)
      }
    }

    "provide all 'Withdrawal' transaction events" in withSparkAndParquetData(data, transactionEventsPath) { implicit sqlContext =>
      withWithdrawalEvents { dataset =>
        val events = dataset.collect
        val withdrawals = data.filter(event => event.kind == TransactionEventType.Withdrawal)

        events should contain theSameElementsAs (withdrawals)
        events.foreach(event => event.kind should be(TransactionEventType.Withdrawal))
      }
    }

    "provide all 'Insertion' transaction events" in withSparkAndParquetData(data, transactionEventsPath) { implicit sqlContext =>
      withInsertionEvents { dataset =>
        val events = dataset.collect
        val insertions = data.filter(event => event.kind == TransactionEventType.Insertion)

        events should contain theSameElementsAs (insertions)
        events.foreach { event => event.kind should be(TransactionEventType.Insertion) }
      }
    }

    "provide all 'Transfer' transaction events" in withSparkAndParquetData(data, transactionEventsPath) { implicit sqlContext =>
      withTransferEvents { dataset =>
        val events = dataset.collect
        val transfers = data.filter(event => event.kind == TransactionEventType.Transfer)

        events should contain theSameElementsAs (transfers)
        events.foreach { event => event.kind should be(TransactionEventType.Transfer) }
      }
    }
  }

  private def withSparkAndParquetData[R](data: List[TransactionEvent], path: String)(f: SQLContext => R): R =
    withSpark[R] { implicit sparkContext =>
      withSparkSql[R]{ implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(data.toDS(), path) { () =>
          f(sqlContext)
        }
      }
    }
}