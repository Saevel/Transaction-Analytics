package org.dmcs.transaction.analyst.tests.framework.loggers

/**
  * Created by Zielony on 2016-10-26.
  */
trait Logger {

  implicit val defaultLogLevel: LogLevel.Value

  def log(message: => Any): Unit
}

object Logger {

  def apply(f: (Any => Unit))(implicit logLevel: LogLevel.Value): Logger  = new Logger {
    override implicit val defaultLogLevel: _root_.org.dmcs.transaction.analyst.tests.framework.loggers.LogLevel.Value = logLevel

    override def log(message: => Any): Unit = f(message)
  }
}
