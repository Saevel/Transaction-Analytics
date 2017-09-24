package org.dmcs.transaction.analytics.tests.framework.reporters

import org.dmcs.transaction.analytics.tests.framework.model.TestResult

import scala.concurrent.Future

trait IdleReporter extends Reporter {

  override protected def report(tests: Traversable[TestResult[_, _]]): Future[_] = Future.successful({})
}
