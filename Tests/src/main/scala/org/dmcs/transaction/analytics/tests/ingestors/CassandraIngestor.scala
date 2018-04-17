package org.dmcs.transaction.analytics.tests.ingestors

import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{ExecutionContext, Future}

object CassandraIngestor {

  def apply(configuration: CassandraConfiguration)
           (implicit sqlContext: SQLContext, ex: ExecutionContext): Ingestor[ApplicationModel] = new Ingestor[ApplicationModel] {
    override def ingest(data: ApplicationModel, phaseId: Int): Unit = {
      import sqlContext.implicits._
      data.entitiesModel.operations.toSeq.toDF.write.cassandraFormat(configuration.operationsTable, configuration.keyspace)
      data.entitiesModel.userAccounts.toSeq.toDF.write.cassandraFormat(configuration.usersTable, configuration.keyspace)
      data.entitiesModel.userData.toSeq.toDF.write.cassandraFormat(configuration.accountsTable, configuration.keyspace)
    }
  }
}
