package org.dmcs.transaction.analytics.speed

import org.apache.spark.sql.Dataset

/**
  * Created by Zielony on 2016-08-03.
  */
package object layer {

  trait DatasetWithAverage {

    def average: Double;
  }

  implicit class IntDatasetWithAverage(data: Dataset[Int]) extends DatasetWithAverage {

    def average: Double = {

      import data.sqlContext.implicits._

      val (sum, count) = data.map(value => (value, 1)).reduce{(first, second) =>
        val (firstSum, firstCount) = first
        val (secondSum, secondCount) = second
        (firstSum + secondSum, firstCount + secondCount)
      }

      (sum / count)
    }
  }

  implicit class DoubleDatasetWithAverage(data: Dataset[Double]) extends DatasetWithAverage {

    def average: Double = {

      import data.sqlContext.implicits._

      val (sum, count) = data.map(value => (value, 1)).reduce{(first, second) =>
        val (firstSum, firstCount) = first
        val (secondSum, secondCount) = second
        (firstSum + secondSum, firstCount + secondCount)
      }

      (sum / count)
    }
  }
}