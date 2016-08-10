package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{TransactionEvent, TransactionEventType}
import org.dmcs.transaction.analytics.lambda.events.TransactionEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait TransactionEventsAdapter extends Spark {

  def withTransactionEvents[T](f:(Dataset[TransactionEvent] => T)): T = {
    withSparkSql { sqlContext =>
     f(sqlContext.read.json("").as[TransactionEvent])
    }
  }

  def withWithdrawalEvents = withKind(Withdrawal)

  def withInsertionEvents = withKind(Insertion)

  def withTransferEvents = withKind(Transfer)

  private[adapters] def withKind[T](kind: TransactionEventType.Value)(f:(Dataset[TransactionEvent] => T)):T = {
    withTransactionEvents { events =>
      f(events.filter(_.kind == kind))
    }
  }
}