package org.dmcs.transaction.analytics.test.framework

trait Equality[T] {

  def areEqual(a: T, b: T): Boolean
}

object Equality {

  def apply[T](f: ((T, T) => Boolean)): Equality[T] = new Equality[T] {
    override def areEqual(a: T, b: T): Boolean = f(a, b)
  }

  def apply[T] = new Equality[T] {
    override def areEqual(a: T, b: T): Boolean = a.equals(b)
  }
}
