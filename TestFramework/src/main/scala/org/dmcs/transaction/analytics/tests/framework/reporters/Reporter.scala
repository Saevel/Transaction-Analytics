package org.dmcs.transaction.analytics.tests.framework.reporters

import org.dmcs.transaction.analytics.tests.framework.model.TestResult

import scala.concurrent.Future

trait Reporter{

  protected def report(tests: Traversable[TestResult[_, _]]): Future[_]
}
