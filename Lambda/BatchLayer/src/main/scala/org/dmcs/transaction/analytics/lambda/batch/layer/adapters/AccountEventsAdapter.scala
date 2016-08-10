package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType}
import org.dmcs.transaction.analytics.lambda.events.AccountEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountEventsAdapter extends Spark {

  def withAccountEvents[T](f:(Dataset[AccountEvent] => T)):T = {
    withSparkSql { sqlContext =>
      f(sqlContext.read.json("").as[AccountEvent])
    }
  }

  def withAccountsCreated = withKind(Created)

  def withAccountsDeleted = withKind(Deleted)

  private[adapters] def withKind[T](kind: AccountEventType.Value)
                                   (f: Dataset[AccountEvent] => T): T =
    withAccountEvents[T] { accountEvents =>
      f(accountEvents.filter(_.kind == kind))
    }
}
