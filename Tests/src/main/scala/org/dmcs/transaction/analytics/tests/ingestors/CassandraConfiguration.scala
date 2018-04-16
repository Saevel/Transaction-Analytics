package org.dmcs.transaction.analytics.tests.ingestors

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class CassandraConfiguration(host:String,
                                  port: Int,
                                  username: String,
                                  password: String,
                                  keyspace: String,
                                  usersTable: String,
                                  accountsTable: String,
                                  operationsTable: String)

object CassandraConfiguration {

  def apply()(implicit bundle: Bundle):  CassandraConfiguration = new CassandraConfiguration(
    %("ingestors.cassandra.host"),
    %("ingestors.cassandra.port"),
    %("ingestors.cassandra.username"),
    %("ingestors.cassandra.password"),
    %("ingestors.cassandra.keyspace"),
    %("ingestors.cassandra.users.table"),
    %("ingestors.cassandra.accounts.table"),
    %("ingestors.cassandra.operations.table")
  )
}