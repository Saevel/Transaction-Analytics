package org.dmcs.transaction.analytics.tests.framework.evaluators

trait Evaluator[DataType, StatisticType] {

  def evaluate(data: Traversable[DataType]): StatisticType

}
