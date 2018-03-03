package org.dmcs.transaction.analytics.tests.framework.reporters

import org.dmcs.transaction.analytics.tests.framework.model.TestResult

import scala.concurrent.Future

trait Reporter{

  protected def report[X](tests: Traversable[TestResult[X]]): Future[_]
}
