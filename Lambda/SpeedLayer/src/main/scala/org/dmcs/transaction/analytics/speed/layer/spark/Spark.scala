package org.dmcs.transaction.analytics.speed.layer.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
  * Created by Zielony on 2016-08-02.
  */
trait Spark {

  def withSpark[T](f: SparkContext => T) = {
    //TODO: Init from properties, a global Spark Context
    val sparkContext:SparkContext = ???
    f(sparkContext)
  }

  def withSparkSql[T](f: SQLContext => T) = withSpark { sparkContext =>
    f(new SQLContext(sparkContext))
  }
}
