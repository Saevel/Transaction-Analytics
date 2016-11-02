package org.dmcs.transaction.analyst.tests.framework.ingestors

/**
  * Created by Zielony on 2016-10-26.
  */
trait MockIngestor[T] extends Ingestor[T] {

  def ingest(data: Traversable[T]): Unit = {}
}
