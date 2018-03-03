package org.dmcs.transaction.analytics.tests.framework.reporters
import org.dmcs.transaction.analytics.tests.framework.model.TestResult

import scala.concurrent.Future
import scala.reflect.io.File

class CsvReporter extends Reporter {

  // TODO: Use Kantan to make CSV

  protected val reportFile: File = ???

  override protected def report[X](tests: Traversable[TestResult[X]]) = {
    // tests.foreach(_.data.)
    ???
  }
}
