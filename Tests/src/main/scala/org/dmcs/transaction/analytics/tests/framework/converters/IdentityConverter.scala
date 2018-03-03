package org.dmcs.transaction.analytics.tests.framework.converters

import org.dmcs.transaction.analytics.tests.framework.data.IdentityDataFormat

// TODO: To trait
class IdentityConverter[T] extends Converter[T, T, IdentityDataFormat[T]] {
  override def convert(item: T): IdentityDataFormat[T] = new IdentityDataFormat[T](item)
}
