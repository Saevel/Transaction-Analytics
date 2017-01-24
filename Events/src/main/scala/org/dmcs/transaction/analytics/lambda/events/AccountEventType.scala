package org.dmcs.transaction.analytics.lambda.events

case class AccountEventType(value: String) extends Serializable {
  override def toString: String = value
}

object AccountEventType extends Serializable {

  val Created = AccountEventType("Created")

  val Deleted = AccountEventType("Deleted")

  def fromString(s: String): AccountEventType = {
    case "Created" => Created
    case "Deleted" => Deleted
    case other => throw new IllegalArgumentException(s"Unmapped value for AccountEventType: $other");
  }
}