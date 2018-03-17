package org.dmcs.transaction.analytics.lambda.speed.layer.queries

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.lambda.speed.layer._

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsQueries {

  def averageClientAge(data:(Dataset[UserData]))(implicit sqlContext: SQLContext): Double = {
    import sqlContext.implicits._
    data.map(_.age).average
  }

  def clientAgeMedian(data: (Dataset[UserData]))(implicit sqlContext: SQLContext): Int = {
    import sqlContext.implicits._
    val half = Math.ceil(data.count / 2.0).toInt
    val sorted = data.rdd.map(_.age).takeOrdered(half)
    sorted.last
  }
}