package org.dmcs.transaction.analytics.speed.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsAdapter extends Spark {

  //TODO: Read actual data
  def withClientData[T](f:(Dataset[UserData] => T)):T = withSparkSql { sqlContext =>
    f(sqlContext.read.json("").as[UserData])
  }
}
