package org.dmcs.transaction.analyst.tests.app.json

import java.sql.Timestamp

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType, UserAccount, UserData}
import org.dmcs.transaction.analyst.tests.app.model.{TestModel, _}
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, TransactionEvent, UserEvent, _}
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by Zielony on 2016-10-28.
  */
object TestModelJsonWrites {

  implicit val timestampWrites: Writes[Timestamp] = new Writes[Timestamp] {
    override def writes(timestamp: Timestamp): JsValue = JsNumber(timestamp.getTime)
  }

  implicit val accountEventTypeWrites: Writes[AccountEventType] = new Writes[AccountEventType]{
    override def writes(kind: AccountEventType): JsValue = JsString(kind.value)
  }

  implicit val userEventTypeWrites: Writes[UserEventType] = new Writes[UserEventType]{
    override def writes(kind: UserEventType): JsValue = JsString(kind.value)
  }

  implicit val transactionEventTypeWrites: Writes[TransactionEventType] = new Writes[TransactionEventType]{
    override def writes(kind: TransactionEventType): JsValue = JsString(kind.value)
  }

  implicit val cashOperationTypeWrites: Writes[CashOperationType] = new Writes[CashOperationType]{
    override def writes(kind: CashOperationType): JsValue = JsString(kind.value)
  }

  implicit val personalDataWrites: Writes[PersonalData] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "surname").write[String] and
      (JsPath \ "age").write[Option[Int]]
    )(unlift(PersonalData.unapply))

  implicit val contactDataWrites: Writes[ContactData] = (
    (JsPath \ "phone").write[Option[String]] and
      (JsPath \ "email").write[Option[String]] and
      (JsPath \ "address").write[Option[String]] and
      (JsPath \ "country").write[Option[String]]
    )(unlift(ContactData.unapply))

  implicit val userEventWrites: Writes[UserEvent] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "timestamp").write[Timestamp] and
      (JsPath \ "username").write[String] and
      (JsPath \ "password").write[String] and
      (JsPath \ "personalData").write[PersonalData] and
      (JsPath \ "contactData").write[ContactData] and
      (JsPath \ "kind").write[UserEventType]
    )(unlift(UserEvent.unapply))

  implicit val transactionEventWrites: Writes[TransactionEvent] = (
    (JsPath \ "timestamp").write[Timestamp] and
      (JsPath \ "sourceAccount").write[Long] and
      (JsPath \ "targetAccount").write[Option[Long]] and
      (JsPath \ "amount").write[Double] and
      (JsPath \ "kind").write[TransactionEventType]
    )(unlift(TransactionEvent.unapply))

  implicit val accountEventWrites: Writes[AccountEvent] = (
    (JsPath \ "timestamp").write[Timestamp] and
      (JsPath \ "userId").write[Long] and
      (JsPath \ "accountId").write[Long] and
      (JsPath \ "balance").write[Double] and
      (JsPath \ "kind").write[AccountEventType]
    )(unlift(AccountEvent.unapply))

  implicit val accountEventsWrites: Writes[AccountEvents] = (
    (JsPath \ "accountsCreated").write[Traversable[AccountEvent]] and
      (JsPath \ "accountsDeleted").write[Traversable[AccountEvent]]
    )(unlift(AccountEvents.unapply))

  implicit val userEventsWrites: Writes[UserEvents] = (
    (JsPath \ "usersCreated").write[Traversable[UserEvent]] and
      (JsPath \ "usersUpdated").write[Traversable[UserEvent]] and
      (JsPath \ "usersDeleted").write[Traversable[UserEvent]]
    )(unlift(UserEvents.unapply))

  implicit val transactionEventsWrites: Writes[TransactionEvents] = (
    (JsPath \ "insertionEvents").write[Traversable[TransactionEvent]] and
      (JsPath \ "withdrawalEvents").write[Traversable[TransactionEvent]] and
      (JsPath \ "transferEvents").write[Traversable[TransactionEvent]]
    )(unlift(TransactionEvents.unapply))

  implicit val eventWrites: Writes[EventsModel] = (
    (JsPath \ "accounts").write[AccountEvents] and
      (JsPath \ "users").write[UserEvents] and
      (JsPath \ "transactions").write[TransactionEvents]
    )(unlift(EventsModel.unapply))

  implicit val userDataWrites: Writes[UserData] = (
    (JsPath \ "userId").write[Long] and
      (JsPath \ "age").write[Int]
    )(unlift(UserData.unapply))

  implicit val userAccountWrites: Writes[UserAccount] = (
    (JsPath \ "userId").write[Long] and
      (JsPath \ "accountId").write[Long] and
      (JsPath \ "balance").write[Double] and
      (JsPath \ "country").write[Option[String]] and
      (JsPath \ "age").write[Option[Int]]
    )(unlift(UserAccount.unapply))

  implicit val cashOperationWrites: Writes[CashOperation] = (
    (JsPath \ "timestamp").write[Timestamp] and
      (JsPath \ "sourceAccountId").write[Long] and
      (JsPath \ "targetAccountId").write[Option[Long]] and
      (JsPath \ "kind").write[CashOperationType] and
      (JsPath \ "amount").write[Double]
    )(unlift(CashOperation.unapply))

  implicit val entitiesWrites: Writes[EntitiesModel] = (
    (JsPath \ "users").write[Traversable[UserData]] and
      (JsPath \ "accounts").write[Traversable[UserAccount]] and
      (JsPath \ "transactions").write[Traversable[CashOperation]] and
      (JsPath \ "originalAccounts").write(new Writes[Traversable[UserAccount]]{
        override def writes(o: Traversable[UserAccount]): JsValue = JsNull
      })
    )(unlift(EntitiesModel.unapply))

  implicit val modelWrites: Writes[TestModel] = (
    (JsPath \ "events").write[EventsModel] and
      (JsPath \ "entities").write[EntitiesModel]
    )(unlift(TestModel.unapply))
}