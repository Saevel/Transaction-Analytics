package org.dmcs.transaction.analytics.tests.framework.ingestors

import org.dmcs.transaction.analytics.tests.framework.data.IdentityDataFormat

import scala.concurrent.{ExecutionContext, Future}

trait IdleIngestor[T] extends Ingestor[SinkType.Empty.type, T, IdentityDataFormat[T]] {

  override protected def ingest(data: Traversable[IdentityDataFormat[T]])(implicit context: ExecutionContext): Future[_] =
    Future.successful({})

  override protected def cleanup(implicit context: ExecutionContext): Future[_] = Future.successful({})
}
