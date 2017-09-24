package org.dmcs.transaction.analytics.tests.framework.data

class AvroDataFormat(bytes: Array[Byte]) extends DataFormat[Array[Byte]]{
  override def data: Array[Byte] = bytes
}
