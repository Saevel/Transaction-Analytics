package org.dmcs.transaction.analytics.test.filters

/**
  * Created by Zielony on 2016-08-05.
  */
trait Filter[DataType] {

  def filter(data: DataType): Boolean
}
