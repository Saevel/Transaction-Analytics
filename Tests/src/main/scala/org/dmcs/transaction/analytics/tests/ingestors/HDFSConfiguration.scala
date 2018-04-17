package org.dmcs.transaction.analytics.tests.ingestors

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class HDFSConfiguration(hdfsUrl: String, usersFolder: String, accountsFolder: String, operationsFolder: String)

object HDFSConfiguration {

  def apply()(implicit bundle: Bundle): HDFSConfiguration = new HDFSConfiguration(
    %("ingestion.hdfs.url"),
    %("ingestion.hdfs.folders.users"),
    %("ingestion.hdfs.folders.accounts"),
    %("ingestion.hdfs.folders.operations")
  )
}