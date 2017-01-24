package org.dmcs.transaction.analytics.lambda.events

case class TransactionEventType(val value: String) extends Serializable {
  override def toString: String = value
}

object TransactionEventType {

  val Insertion = TransactionEventType("Insertion")

  val Withdrawal = TransactionEventType("Withdrawal")

  val Transfer = TransactionEventType("Transfer")

  def fromString(s: String): TransactionEventType = {
    case "Insertion" => Insertion
    case "Withdrawal" => Withdrawal
    case "Transfer" => Transfer
    case other => throw new IllegalArgumentException(s"Unmapped value for AccountEventType: $other");
  }
}
