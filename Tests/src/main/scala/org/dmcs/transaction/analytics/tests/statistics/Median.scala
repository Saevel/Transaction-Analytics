package org.dmcs.transaction.analytics.tests.statistics

import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic

object Median extends Statistic[Int, Int]{

  override def evaluate(data: Seq[Int]) = if(data isEmpty) {
    0
  }
  else if(data.size % 2 == 0){
    data((data.size / 2))
  } else {
    (data((data.size / 2) + 1) + data((data.size / 2) - 1)) / 2
  }
}
