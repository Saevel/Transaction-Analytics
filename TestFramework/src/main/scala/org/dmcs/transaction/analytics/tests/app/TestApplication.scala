package org.dmcs.transaction.analytics.tests.app

import org.dmcs.transaction.analytics.tests.framework.model.TestApp
import org.dmcs.transaction.analytics.tests.framework.reporters.IdleReporter

import scala.concurrent.ExecutionContext.Implicits.global

object TestApplication extends TestApp with IdleReporter {

  // execute(ExampleTest)
}
