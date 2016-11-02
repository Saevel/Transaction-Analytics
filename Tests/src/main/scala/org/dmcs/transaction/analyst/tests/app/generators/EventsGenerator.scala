package org.dmcs.transaction.analyst.tests.app.generators

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType, UserAccount, UserData}
import org.dmcs.transaction.analyst.tests.app.model._
import org.dmcs.transaction.analytics.lambda.events._
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-10-31.
  */
trait EventsGenerator {

  private[generators] def accountEventsGenerator(originalAccounts: Traversable[UserAccount]): Gen[AccountEvents] = {
    val accountsCreated = originalAccounts.map( account =>
      AccountEvent(Timestamp.valueOf(LocalDateTime.now()), account.userId, account.accountId, account.balance, AccountEventType.Created)
    )
    Gen.const(AccountEvents(accountsCreated, List.empty[AccountEvent]))
  }

  private[generators] def transactionEventsGenerator(cashOperations: Traversable[CashOperation]): Gen[TransactionEvents] = {

    val withdrawals = cashOperations.filter(_.kind == CashOperationType.Withdrawal)
    val insertions = cashOperations.filter(_.kind == CashOperationType.Insertion)
    val transfers = cashOperations.filter(_.kind == CashOperationType.Transfer)

    TransactionEvents(
      insertions.map(toEvent),
      withdrawals.map(toEvent),
      transfers.map(toEvent)
    )
  }

  private[generators] def userEventsGenerator(users: Traversable[UserData], countries: String*): Gen[UserEvents] = for {
    username <- Gen.alphaStr
    password <- Gen.alphaStr
    name <- Gen.alphaStr
    surname <- Gen.alphaStr
    phone <- Gen.option(Gen.numStr)
    email <- Gen.option(Gen.alphaStr)
    address <- Gen.option(Gen.alphaStr)
    country <- Gen.option(Gen.oneOf(countries))
  } yield UserEvents(
    users.map( user =>
      UserEvent(user.userId, Timestamp.valueOf(LocalDateTime.now), username, password,
        PersonalData(name, surname, Option(user.age)), ContactData(phone, email, address, country),
        UserEventType.Created
      )
    ),
    List.empty[UserEvent],
    List.empty[UserEvent]
  )

  def eventsModelGenerator(entities: EntitiesModel, countries: String*): Gen[EventsModel] = {
    for {
      userEvents <- userEventsGenerator(entities.users, countries: _*)
      accountEvents <- accountEventsGenerator(entities.accounts)
      transactionEvents <- transactionEventsGenerator(entities.transactions)
    } yield EventsModel(accountEvents, userEvents, transactionEvents)
  }

  private def toEvent(operation: CashOperation): TransactionEvent = TransactionEvent(
    operation.timestamp, operation.sourceAccountId, operation.targetAccountId, operation.amount,
    operation.kind match {
      case CashOperationType.Insertion => TransactionEventType.Insertion
      case CashOperationType.Withdrawal => TransactionEventType.Withdrawal
      case CashOperationType.Transfer => TransactionEventType.Transfer
    }
  )
}