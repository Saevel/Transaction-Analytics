package org.dmcs.transaction.analytics.tests.framework.ingestors

trait SinkType{}

object SinkType {

  object Hdfs extends SinkType

  object Cassandra extends SinkType

  object Empty extends SinkType
}