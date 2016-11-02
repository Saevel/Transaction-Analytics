package org.dmcs.transaction.analyst.tests.framework.generators

import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by Zielony on 2016-10-12.
  */
trait Generator[T] {

  def generate(implicit timeout: FiniteDuration): T
}

object Generator {

  def apply[T](f: (() => T)): Generator[T] = new Generator[T] {
    override def generate(implicit timeout: FiniteDuration): T = {
      f();
    }
  }
}
