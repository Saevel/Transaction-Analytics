package org.dmcs.transaction.analytics.test.evaluators

import scala.concurrent.Future

/**
  * Created by Zielony on 2016-08-04.
  */
trait Evaluator[ResultType] {

  def evaluate(expected: ResultType, actual: Either[Exception, ResultType]): Double
}
