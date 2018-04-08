package org.dmcs.transaction.analytics.tests.statistics

import org.dmcs.transaction.analytics.test.framework.components.statistics.Statistic

object Count extends Statistic[_, Long]{
  override def evaluate(data: Seq[_]): Long = data.size.toLong
}
