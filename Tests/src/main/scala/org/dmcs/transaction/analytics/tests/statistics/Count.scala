package org.dmcs.transaction.analytics.tests.statistics

import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic

class Count[T] extends Statistic[T, Long]{
  override def evaluate(data: Seq[T]): Long = data.size.toLong
}
