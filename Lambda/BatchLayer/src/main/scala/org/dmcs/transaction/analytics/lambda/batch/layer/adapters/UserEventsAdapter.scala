package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.UserEventType
import org.dmcs.transaction.analytics.lambda.events.UserEvent
import org.dmcs.transaction.analytics.lambda.events.UserEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait UserEventsAdapter extends Spark {

  val userEventsPath: String

  def withUserEvents[T](f:(Dataset[UserEvent] => T))(implicit sqlContext: SQLContext): T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(userEventsPath).as[UserEvent])
  }

  def withUsersCreated[T](f: (Dataset[UserEvent] => T))(implicit sqlContext: SQLContext): T =
    withUserEvents[T]{ events =>
      f(events.filter(event => event.kind == UserEventType.Created))
    }

  def withUsersUpdated[T](f: (Dataset[UserEvent] => T))(implicit sqlContext: SQLContext): T =
    withUserEvents[T]{ events =>
      f(events.filter(event => event.kind == UserEventType.Updated))
    }

  def withUsersDeleted[T](f: (Dataset[UserEvent] => T))(implicit sqlContext: SQLContext): T =
    withUserEvents[T]{ events =>
      f(events.filter(event => event.kind == UserEventType.Deleted))
    }
}