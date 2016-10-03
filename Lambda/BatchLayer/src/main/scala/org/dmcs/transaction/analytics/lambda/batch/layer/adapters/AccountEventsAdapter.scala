package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType}
import org.dmcs.transaction.analytics.lambda.events.AccountEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountEventsAdapter extends Spark {

  val accountEventsPath: String

  def withAccountEvents[T](f:(Dataset[AccountEvent] => T))(implicit sqlContext: SQLContext):T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(accountEventsPath).as[AccountEvent])
  }

  def withAccountsCreated[T](f: (Dataset[AccountEvent] => T))(implicit sqlContext: SQLContext): T =
    withAccountEvents[T]{ events =>
      f(events.filter(event => event.kind == AccountEventType.Created))
    }

  def withAccountsDeleted[T](f: (Dataset[AccountEvent] => T))(implicit sqlContext: SQLContext): T =
    withAccountEvents[T]{ events =>
      f(events.filter(event => event.kind == AccountEventType.Deleted))
    }
}