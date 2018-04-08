package org.dmcs.transaction.analytics.test.framework.components.ingestors

import scala.concurrent.Future

object IdleIngestor extends Ingestor {

  override def ingest[T](data: T): Future[_] = Future.successful({})
}
