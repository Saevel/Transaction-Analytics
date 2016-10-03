package org.dmcs.transaction.analytics.lambda.events

case class TransactionEventType(val value: String)

object TransactionEventType {

  val Insertion = TransactionEventType("Insertion")

  val Withdrawal = TransactionEventType("Withdrawal")

  val Transfer = TransactionEventType("Transfer")
}
