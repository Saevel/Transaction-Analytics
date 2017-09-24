package org.dmcs.transaction.analytics.tests.framework.model

/**
  * Created by kamil on 2017-09-13.
  */
trait Equallity[T] {

  def areEqual(first: T, second: T): Boolean
}
