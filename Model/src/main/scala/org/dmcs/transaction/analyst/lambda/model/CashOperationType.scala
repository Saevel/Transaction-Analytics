package org.dmcs.transaction.analyst.lambda.model

case class CashOperationType(value: String)

/**
  * Created by Zielony on 2016-08-03.
  */
object CashOperationType {

  val Withdrawal = CashOperationType("Withdrawal")

  val Insertion = CashOperationType("Insertion")

  val Transfer = CashOperationType("Transfer")
}
