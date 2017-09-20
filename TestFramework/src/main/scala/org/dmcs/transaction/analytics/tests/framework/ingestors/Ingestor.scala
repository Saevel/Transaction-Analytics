package org.dmcs.transaction.analytics.tests.framework.ingestors

import org.dmcs.transaction.analytics.tests.framework.converters.Converter
import org.dmcs.transaction.analytics.tests.framework.data.DataFormat

import scala.concurrent.{ExecutionContext, Future}

trait Ingestor[ST <: SinkType, FormattedDataType, Format <: DataFormat[FormattedDataType]] {

  protected def ingest(data: Traversable[Format])(implicit context: ExecutionContext): Future[_]

  protected def cleanup(implicit context: ExecutionContext): Future[_]

  // FIXME: Unusable due to identical signature?
  /*
  protected def ingest[DataType](data: Traversable[DataType])(implicit converter: Converter[DataType, FormattedDataType, Format],
    context: ExecutionContext): Future[_] = ingest(data.map(converter.convert))
    */
}
