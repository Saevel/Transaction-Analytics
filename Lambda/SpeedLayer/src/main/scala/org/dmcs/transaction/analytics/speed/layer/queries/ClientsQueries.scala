package org.dmcs.transaction.analytics.speed.layer.queries

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.speed.layer._

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsQueries {

  def averageClientAge(data:(Dataset[UserData])): Double = {
    import data.sqlContext.implicits._
    data.map(_.age).average
  }

  def clientAgeMedian(data: (Dataset[UserData])): Double = {
    import data.sqlContext.implicits._
    val half = Math.floorDiv(data.count, 2).toInt
    val sorted = data.rdd.sortBy(_.age).take(half)
    sorted.last.age
  }
}
