package org.dmcs.transaction.analytics.tests.ingestors

import prv.zielony.proper.model.Bundle

case class IngestorsConfiguration(hdfs: HDFSConfiguration, cassandra: CassandraConfiguration)

object IngestorsConfiguration {

  def apply()(implicit bundle: Bundle): IngestorsConfiguration = new IngestorsConfiguration(
    HDFSConfiguration(),
    CassandraConfiguration()
  )
}
