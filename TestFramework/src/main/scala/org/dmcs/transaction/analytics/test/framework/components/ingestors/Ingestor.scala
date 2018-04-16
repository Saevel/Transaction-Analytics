package org.dmcs.transaction.analytics.test.framework.components.ingestors

import org.dmcs.transaction.analytics.test.framework.schema.Schema

import scala.concurrent.Future

trait Ingestor[T] {

  def ingest(data: T, phaseId: Int): Unit
}