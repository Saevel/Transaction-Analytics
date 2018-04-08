package org.dmcs.transaction.analytics.tests.statistics

import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic


object DoubleAverage extends Statistic[Double, Double] {
  override def evaluate(data: Seq[Double]) = if(data nonEmpty) {
    data.fold(0.0)(_ + _) / data.size
  } else 0.0
}
