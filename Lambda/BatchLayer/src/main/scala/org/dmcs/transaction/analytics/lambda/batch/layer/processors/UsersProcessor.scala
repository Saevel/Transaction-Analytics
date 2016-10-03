package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.UserEventsAdapter

/**
  * Created by Zielony on 2016-08-03.
  */
trait UsersProcessor extends UserEventsAdapter {

  def constructUsers(implicit sqlContext: SQLContext): Dataset[UserData] = {
    withUsersCreated { created =>
      withUsersUpdated { updated =>
        withUsersDeleted { deleted =>
          //TODO: Build users
          ???
        }
      }
    }
  }

}
