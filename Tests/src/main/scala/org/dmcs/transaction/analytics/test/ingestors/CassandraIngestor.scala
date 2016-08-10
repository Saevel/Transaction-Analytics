package org.dmcs.transaction.analytics.test.ingestors

/**
  * Created by Zielony on 2016-08-07.
  */
class CassandraIngestor[DataType] extends Ingestor[DataType] {

  override def ingest(data: Traversable[DataType]): Unit = {

  }
}
