package org.dmcs.transaction.analytics.tests.framework.evaluators

trait UnitEvaluator[T] extends Evaluator[T, Unit] {
  override def evaluate(data: Traversable[T]): Unit = {}
}
