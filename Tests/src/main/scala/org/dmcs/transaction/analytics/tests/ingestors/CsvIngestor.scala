package org.dmcs.transaction.analytics.tests.ingestors

import java.io.{File, FileWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.tests.model.ApplicationModel

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object CsvIngestor {

  def apply(path: String)(implicit sqlContext: SQLContext, ex: ExecutionContext): Ingestor[ApplicationModel] = new Ingestor[ApplicationModel] {
    override def ingest(data: ApplicationModel): Future[_] = Future {
      import sqlContext.implicits._
      data.eventsModel.userEvents.toSeq.toDF.write.json(s"$path/user-events")
      data.eventsModel.transactionEvents.toSeq.toDF.write.json(s"$path/transaction-events")
      data.eventsModel.accountEvents.toSeq.toDF.write.json(s"$path/account-events")
      data.entitiesModel.userData.toSeq.toDF.write.json(s"$path/users")
      data.entitiesModel.userAccounts.toSeq.toDF.write.json(s"$path/accounts")
      data.entitiesModel.operations.toSeq.toDF.write.json(s"$path/operations")
    }
  }
}
