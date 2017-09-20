package org.dmcs.transaction.analytics.tests.framework.evaluators

trait MeanEvaluator extends Evaluator[Double, Double] {

  override def evaluate(data: Traversable[Double]): Double = (data.sum / data.size)
}
