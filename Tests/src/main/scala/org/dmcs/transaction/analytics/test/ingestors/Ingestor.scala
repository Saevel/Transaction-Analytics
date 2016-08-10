package org.dmcs.transaction.analytics.test.ingestors

/**
  * Created by Zielony on 2016-08-04.
  */
trait Ingestor[IngestedType] {

  def ingest(data: Traversable[IngestedType]): Unit

}
