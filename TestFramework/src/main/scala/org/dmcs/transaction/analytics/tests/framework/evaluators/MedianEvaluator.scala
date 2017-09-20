package org.dmcs.transaction.analytics.tests.framework.evaluators

trait MedianEvaluator[X] extends Evaluator[X, X] {

  // TODO: Context bounds / implicits?
  override def evaluate(data: Traversable[X]): X = ???
    // data.toSeq.sorted.apply(Math.ceil(data.size / 2).toInt)
}
