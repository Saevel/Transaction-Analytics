package org.dmcs.transaction.analytics.tests.framework.converters

import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s._
import org.dmcs.transaction.analytics.tests.framework.data.AvroDataFormat

class ScalaAvroConverter[X](implicit schema: SchemaFor[X], toRecord: ToRecord[X]) extends Converter[X, Array[Byte], AvroDataFormat]{

  override def convert(item: X): AvroDataFormat = {
    val byteArrayStream = new ByteArrayOutputStream()
    val avroStream = AvroOutputStream.data(byteArrayStream)
    avroStream.write(item)
    avroStream.close
    new AvroDataFormat(byteArrayStream.toByteArray)
  }
}
