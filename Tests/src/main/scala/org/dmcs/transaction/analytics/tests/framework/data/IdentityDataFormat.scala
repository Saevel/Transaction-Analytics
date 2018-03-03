package org.dmcs.transaction.analytics.tests.framework.data

// TODO: To tests?
class IdentityDataFormat[T](t: T) extends DataFormat[T] {
  override def data: T = t
}
