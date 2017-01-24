package org.dmcs.transaction.analytics.lambda.events

case class UserEventType(val value: String) extends Serializable {
  override def toString: String = value
}

object UserEventType extends Serializable{

  val Created = new UserEventType("Created")

  val Deleted = new UserEventType("Deleted")

  val Updated = new UserEventType("Updated")

  def fromString(s: String): UserEventType = {
    case "Created" => Created
    case "Deleted" => Deleted
    case "Updated" => Updated
    case other => throw new IllegalArgumentException(s"Unmapped value for AccountEventType: $other");
  }

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
