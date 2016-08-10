package org.dmcs.transaction.analytics.lambda.batch.layer

import org.dmcs.transaction.analytics.lambda.batch.layer.processors.{AccountsProcessor, CashOperationsProcessor, UsersProcessor}

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends App with AccountsProcessor with UsersProcessor with CashOperationsProcessor {

  //TODO: Read properties

  //TODO: Real data!

  constructAccounts.toDF().write.json("")

  constructUsers.toDF().write.json("")

  constructCashOperations.toDF().write.json("")
}
