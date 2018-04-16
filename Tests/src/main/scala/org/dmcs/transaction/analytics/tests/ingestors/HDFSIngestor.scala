package org.dmcs.transaction.analytics.tests.ingestors

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{ExecutionContext, Future}

object HDFSIngestor {

  private val formatter = DateTimeFormatter.ISO_DATE_TIME

  def apply(configuration: HDFSConfiguration)
           (implicit sqlContext: SQLContext, ex: ExecutionContext): Ingestor[ApplicationModel] = new Ingestor[ApplicationModel]{
    override def ingest(data: ApplicationModel) = Future {
      import sqlContext.implicits._
      val now = LocalDateTime.now.format(formatter)
      data.eventsModel.userEvents.toSeq.toDF().write.parquet(configuration.hdfsUrl + configuration.usersFolder + now)
      data.eventsModel.accountEvents.toSeq.toDF().write.parquet(configuration.hdfsUrl + configuration.accountsFolder + now)
      data.eventsModel.transactionEvents.toSeq.toDF().write.parquet(configuration.hdfsUrl + configuration.operationsFolder + now)
    }
  }
}