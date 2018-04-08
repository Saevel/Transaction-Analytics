package org.dmcs.transaction.analytics.test.framework.components.ingestors

import org.dmcs.transaction.analytics.test.framework.schema.Schema

import scala.concurrent.Future

trait Ingestor {

  def ingest[T](data: T)(implicit schema: Schema[T]): Future[_]
}