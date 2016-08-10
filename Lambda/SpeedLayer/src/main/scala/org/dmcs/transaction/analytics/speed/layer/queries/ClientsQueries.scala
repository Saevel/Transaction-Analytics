package org.dmcs.transaction.analytics.speed.layer.queries

/**
  * Created by Zielony on 2016-08-03.
  */
trait ClientsQueries {

  def averageClientAge(data:(Dataset[UserData])) = data.map(_.age).average

  def clientAgeMedian(data: (Dataset[UserData])) = {
    val half = Math.floorDiv(data.count, 2).toInt
    val sorted = data.rdd.sortBy(_.age).takeOrdered(half)
    sorted.last.age
  }
}
