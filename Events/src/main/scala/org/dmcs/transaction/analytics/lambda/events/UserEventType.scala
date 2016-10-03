package org.dmcs.transaction.analytics.lambda.events

case class UserEventType(val value: String) extends Serializable;

object UserEventType extends Serializable{

  val Created = new UserEventType("Created")

  val Deleted = new UserEventType("Deleted")

  val Updated = new UserEventType("Updated")

  /*

  case object Deleted extends UserEventType {
    override val value: String = "Deleted"
  }

  case object Updated extends UserEventType {
    override val value: String = "Updated"
  }

  */
}

/*
object UserEventType extends Enumeration {

  val Created, Deleted, Updated = Value;
}
*/
