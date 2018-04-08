package org.dmcs.transaction.analytics.tests.ingestors

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.schema.Schema

import scala.concurrent.Future

object CassandraIngestor {

  def apply(implicit sqlContext: SQLContext): Ingestor = new Ingestor {
    override def ingest[T](data: T)(implicit schema: Schema[T]): Future[_] = Future {

    }
  }
}
