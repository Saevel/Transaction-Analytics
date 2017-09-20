package org.dmcs.transaction.analytics.tests.app

import scala.concurrent.ExecutionContext

trait GlobalExecutionContext {

  implicit val context: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}
