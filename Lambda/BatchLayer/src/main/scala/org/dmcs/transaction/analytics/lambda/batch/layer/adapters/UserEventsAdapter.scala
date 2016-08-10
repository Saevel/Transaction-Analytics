package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.{UserEvent, UserEventType}
import org.dmcs.transaction.analytics.lambda.events.UserEventType._

/**
  * Created by Zielony on 2016-08-03.
  */
trait UserEventsAdapter extends Spark {

  def withUserEvents[T](f:(Dataset[UserEvent] => T)):T = {
    withSparkSql { sqlContext =>
      f(sqlContext.read.json("").as[UserEvent])
    }
  }

  def withUsersCreated = withKind(Created)

  def withUsersUpdated = withKind(Updated)

  def withUsersDeleted = withKind(Deleted)

  private[adapters] def withKind[T](kind: UserEventType.Value)(f:(Dataset[UserEvent] => T)): T = {
    withUserEvents { events =>
      f(events.filter(_.kind == kind))
    }
  }
}
