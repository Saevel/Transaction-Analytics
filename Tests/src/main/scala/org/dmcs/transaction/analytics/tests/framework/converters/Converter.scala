package org.dmcs.transaction.analytics.tests.framework.converters

import org.dmcs.transaction.analytics.tests.framework.data.DataFormat

trait Converter[T, X, Y <: DataFormat[X]] {

  def convert(item: T): Y
}
