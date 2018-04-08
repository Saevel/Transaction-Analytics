package org.dmcs.transaction.analytics.tests.statistics

import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic

object IntAverage extends Statistic[Int, Double] {
  override def evaluate(data: Seq[Int]) = if(data nonEmpty){
    data.foldLeft(0)(_ + _) / data.size
  } else 0
}
