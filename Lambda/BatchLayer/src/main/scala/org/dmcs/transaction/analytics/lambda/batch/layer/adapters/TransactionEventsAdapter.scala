package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{TransactionEvent, TransactionEventType}
import org.dmcs.transaction.analytics.lambda.events.TransactionEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait TransactionEventsAdapter extends Spark {

  val transactionEventsPath: String;

  def withTransactionEvents[T](f:(Dataset[TransactionEvent] => T))(implicit sqlContext: SQLContext): T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(transactionEventsPath).as[TransactionEvent])
  }

  def withWithdrawalEvents[T](f: (Dataset[TransactionEvent] => T))(implicit sqlContext: SQLContext): T =
    withTransactionEvents[T]{ events =>
      f(events.filter(event => event.kind == TransactionEventType.Withdrawal))
    }

  def withInsertionEvents[T](f: (Dataset[TransactionEvent] => T))(implicit sqlContext: SQLContext): T =
    withTransactionEvents[T]{ events =>
      f(events.filter(event => event.kind == TransactionEventType.Insertion))
    }

  def withTransferEvents[T](f: (Dataset[TransactionEvent] => T))(implicit sqlContext: SQLContext): T =
    withTransactionEvents[T]{ events =>
      f(events.filter(event => event.kind == TransactionEventType.Transfer))
    }
}