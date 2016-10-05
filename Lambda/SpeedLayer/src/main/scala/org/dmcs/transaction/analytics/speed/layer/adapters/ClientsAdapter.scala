package org.dmcs.transaction.analytics.speed.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsAdapter extends Spark {

  val clientsPath: String

  //TODO: Read actual data
  def withClientData[T](f:(Dataset[UserData] => T))(implicit sqlContext: SQLContext): T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(clientsPath).as[UserData])
  }
}
