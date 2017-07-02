package org.dmcs.transaction.analyst.lambda.model

case class CashOperationType(value: String) {
  override def toString: String = value
}


object CashOperationType {

  val Withdrawal = CashOperationType("Withdrawal")

  val Insertion = CashOperationType("Insertion")

  val Transfer = CashOperationType("Transfer")

  val fromString: (String => CashOperationType) = {
    case "Withdrawal" => Withdrawal
    case "Insertion" => Insertion
    case "Transfer" => Transfer
    case other => throw new IllegalArgumentException(s"Unmapped value for CashOperationType: $other")
  }
}
