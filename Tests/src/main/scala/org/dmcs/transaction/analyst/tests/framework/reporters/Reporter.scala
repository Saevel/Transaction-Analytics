package org.dmcs.transaction.analyst.tests.framework.reporters

import java.io.File

import org.dmcs.transaction.analyst.tests.framework.model.TestResult

/**
  * Created by Zielony on 2016-10-28.
  */
trait Reporter {

  val reportOutput: File

  def report(results: Traversable[TestResult]): Unit
}

object Reporter {

  def apply(file: File)(f: (Traversable[TestResult] => Unit)): Reporter = new Reporter {
    override val reportOutput: File = file

    override def report(results: Traversable[TestResult]): Unit = f(results)
  }
}
