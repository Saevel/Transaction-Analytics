package org.dmcs.transaction.analytics.lambda.events

case class AccountEventType(value: String) extends Serializable;

object AccountEventType extends Serializable {

  val Created = AccountEventType("Created")

  val Deleted = AccountEventType("Deleted")
}
