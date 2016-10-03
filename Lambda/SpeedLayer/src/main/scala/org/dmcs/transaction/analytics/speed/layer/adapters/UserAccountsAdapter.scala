package org.dmcs.transaction.analytics.speed.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-03.
  */
trait UserAccountsAdapter extends Spark {

  //TODO: Read real data!
  def withUserAccounts[T](f: Dataset[UserAccount] => T): T = withSparkSql { sqlContext =>
    import sqlContext.implicits._
    f(sqlContext.read.json("").as[UserAccount])
  }
}
