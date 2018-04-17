package org.dmcs.transaction.analytics.test.framework.components.reporters

import org.dmcs.transaction.analytics.test.framework.TestResult

import scala.concurrent.{ExecutionContext, Future}

trait Reporter {

  def report(results: Traversable[TestResult[_]])(implicit ex: ExecutionContext): Future[_]
}

object Reporter {
  def apply(f: ((Traversable[TestResult[_]] => Future[_]))): Reporter = new Reporter {
    override def report(results: Traversable[TestResult[_]])(implicit ex: ExecutionContext): Future[_] = f(results)
  }

  def apply(f: ((Traversable[TestResult[_]], ExecutionContext) => Future[_])): Reporter = new Reporter {
    override def report(results: Traversable[TestResult[_]])(implicit ex: ExecutionContext): Future[_] = f(results, ex)
  }
}
