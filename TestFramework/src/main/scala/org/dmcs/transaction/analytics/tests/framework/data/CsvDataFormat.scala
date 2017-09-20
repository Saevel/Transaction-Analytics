package org.dmcs.transaction.analytics.tests.framework.data

class CsvDataFormat(items: Traversable[Any]) extends DataFormat[String]{
  override def data: String = items.mkString(",")
}
