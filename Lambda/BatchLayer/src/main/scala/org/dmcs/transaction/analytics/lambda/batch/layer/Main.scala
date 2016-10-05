package org.dmcs.transaction.analytics.lambda.batch.layer

import org.dmcs.transaction.analytics.lambda.batch.layer.processors.{AccountsProcessor, CashOperationsProcessor, UsersProcessor}
import prv.zielony.proper.inject._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends App with AccountsProcessor with UsersProcessor with CashOperationsProcessor {

  val mdsProperties = load(classpath :/ "mds.properties")
  val batchProperties = load(classpath :/ "batch.properties")
  //TODO: Read from properties.

  override val userEventsPath: String = %("mds.events.users" @@ mdsProperties)
  override val transactionEventsPath: String = %("mds.events.transactions" @@ mdsProperties)
  override val accountEventsPath: String = %("mds.events.accounts" @@ mdsProperties)

  val accountsPath: String = %("batch.views.accounts" @@ batchProperties)
  val usersPath: String = %("batch.views.users" @@ batchProperties)
  val cashOperationsPath: String = %("batch.views.cash.operations" @@ batchProperties)

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