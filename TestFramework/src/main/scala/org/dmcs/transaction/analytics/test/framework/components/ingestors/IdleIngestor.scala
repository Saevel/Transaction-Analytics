package org.dmcs.transaction.analytics.test.framework.components.ingestors

import scala.concurrent.Future

object IdleIngestor extends Ingestor[Any] {

  override def ingest(data: Any): Future[_] = Future.successful({})
}
