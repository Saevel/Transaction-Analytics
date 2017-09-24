package org.dmcs.transaction.analytics.tests.framework.model

import scala.util.Failure

trait TestResult[X, Y] {

  val data: Traversable[X]
}

case class TestSuccess[X, Y](data: Traversable[X], actual: Y, expected: Y) extends TestResult[X, Y];

case class TestError[X, Y](data: Traversable[X], message: String, cause: Throwable) extends TestResult[X, Y];

case class TestFailure[X, Y](data: Traversable[X], actual: Y, expected: Y) extends TestResult[X, Y];

object TestError {

  def apply[X, Y](data: Traversable[X], failure: Failure[_]): TestError[X, Y] = new TestError[X, Y](data, failure.exception.getMessage, failure.exception)

  def apply[X, Y](data: Traversable[X], failure: Failure[_], message: String): TestError[X, Y] = new TestError[X, Y](data, message, failure.exception)
}
