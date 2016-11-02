package org.dmcs.transaction.analyst.tests.framework.loggers

/**
  * Created by Zielony on 2016-10-26.
  */
trait ConsoleLogger extends Logger {

  def log(message: => Any): Unit = {
    //TODO: Improve
    println(message.toString);
  }
}
