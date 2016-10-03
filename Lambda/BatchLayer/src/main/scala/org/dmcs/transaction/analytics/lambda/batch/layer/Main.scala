package org.dmcs.transaction.analytics.lambda.batch.layer

import org.dmcs.transaction.analytics.lambda.batch.layer.processors.{AccountsProcessor, CashOperationsProcessor, UsersProcessor}

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends App with AccountsProcessor with UsersProcessor with CashOperationsProcessor {

  //TODO: Read from properties.

  override val userEventsPath: String = ""
  override val transactionEventsPath: String = ""
  override val accountEventsPath: String = ""

  val accountsPath: String = ""
  val usersPath: String = ""
  val cashOperationsPath: String = ""

  //TODO: Read properties

  //TODO: Real data!

  withSpark { implicit sparkContext =>
    withSparkSql { implicit sqlContext =>
      import sqlContext.implicits._
      constructAccounts.toDF().write.parquet(accountsPath)
      constructUsers.toDF().write.parquet(usersPath)
      constructCashOperations.toDF().write.parquet(cashOperationsPath)
    }
  }
}