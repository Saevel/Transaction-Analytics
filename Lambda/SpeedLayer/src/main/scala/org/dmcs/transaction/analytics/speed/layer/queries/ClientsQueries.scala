package org.dmcs.transaction.analytics.speed.layer.queries

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.speed.layer._

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsQueries {

  def averageClientAge(data:(Dataset[UserData]))(implicit sqlContext: SQLContext): Double = {
    import sqlContext.implicits._
    data.map(_.age).average
  }

  def clientAgeMedian(data: (Dataset[UserData]))(implicit sqlContext: SQLContext): Double = {
    import sqlContext.implicits._
    val half = Math.floorDiv(data.count, 2).toInt
    val sorted = data.rdd.sortBy(_.age).take(half)
    sorted.last.age
  }
}
