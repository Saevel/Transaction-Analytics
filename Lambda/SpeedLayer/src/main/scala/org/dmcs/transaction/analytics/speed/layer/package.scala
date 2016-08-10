package org.dmcs.transaction.analytics.speed

import org.apache.spark.sql.Dataset

/**
  * Created by Zielony on 2016-08-03.
  */
package object layer {

  implicit class NumericAverage[T : Numeric[T]](data: Dataset[T]) {

    val operations = implicitly[Numeric[T]]

    def average: Double = {
      val (sum, count) = data map(value => (value, 1)) reduce { (first, second) =>
        val (firstSum, firstCount) = first
        val (secondSum, secondCount) = second
        (operations.plus(firstSum, secondSum), firstCount + secondCount)
      }

      operations.toLong(sum) / count
    }
  }
}
