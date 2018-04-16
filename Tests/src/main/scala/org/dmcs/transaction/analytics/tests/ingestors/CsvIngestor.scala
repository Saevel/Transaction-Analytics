package org.dmcs.transaction.analytics.tests.ingestors

import java.io.{File, FileWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Random, Try}

object CsvIngestor {

  def apply(path: String)(implicit sqlContext: SQLContext, ex: ExecutionContext): Ingestor[ApplicationModel] = new Ingestor[ApplicationModel] {
    override def ingest(data: ApplicationModel, phaseId: Int): Unit = {
      import sqlContext.implicits._
      data.eventsModel.userEvents.toSeq.toDF.write.json(s"$path/$phaseId/user-events")
      data.eventsModel.transactionEvents.toSeq.toDF.write.json(s"$path/$phaseId/transaction-events")
      data.eventsModel.accountEvents.toSeq.toDF.write.json(s"$path/$phaseId/account-events")
      data.entitiesModel.userData.toSeq.toDF.write.json(s"$path/$phaseId/users")
      data.entitiesModel.userAccounts.toSeq.toDF.write.json(s"$path/$phaseId/accounts")
      data.entitiesModel.operations.toSeq.toDF.write.json(s"$path/$phaseId/operations")
    }
  }
}
