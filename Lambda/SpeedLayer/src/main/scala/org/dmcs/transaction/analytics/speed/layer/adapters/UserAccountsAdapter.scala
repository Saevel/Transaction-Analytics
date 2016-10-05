package org.dmcs.transaction.analytics.speed.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-03.
  */
trait UserAccountsAdapter {

  val userAccountsPath: String

  def withUserAccounts[T](f: Dataset[UserAccount] => T)(implicit sqlContext: SQLContext): T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(userAccountsPath).as[UserAccount])
  }
}
