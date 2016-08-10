package org.dmcs.transaction.analyst.lambda.model

/**
  * Created by Zielony on 2016-08-02.
  */
case class UserAccount(userId: Long,
                       accountId: Long,
                       balance: Double,
                       country: String,
                       age: Int)
