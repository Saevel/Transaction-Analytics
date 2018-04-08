package org.dmcs.transaction.analytics.test.framework.schema

trait Field {
  val name: String
  val dataType: DataType
}

trait DataType

object StringType extends DataType

object IntType extends DataType

object DoubleType extends DataType

object FloatType extends DataType

object LongType extends DataType

class ListType[T] extends DataType

class MapType[A, B] extends DataType

class DateType extends DataType

class TimestampType extends DataType

class ObjectType(fields: Seq[Field]) extends DataType
