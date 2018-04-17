package org.dmcs.transaction.analytics.tests.generators.entities

import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.lambda.events._
trait AccountDataFactory {

  protected def accountData(userEvents: Traversable[UserEvent],
                            accountEvents: Traversable[AccountEvent],
                            transactionEvents: Traversable[TransactionEvent]): Traversable[UserAccount] =
    userEvents
      .filter(notDeletedUsers(userEvents))
      .groupBy(_.id)
      .map(toFinalIdAgeAndCountry)
      .map(joinWith(accountEvents))
      .flatMap{case (user, accountEvents) => toUserAccounts(user, accountEvents)}
      .map(joinWith(transactionEvents))

  private[generators] def createdAndNotDeleted(accountEvents: Traversable[AccountEvent])(accountEvent: AccountEvent): Boolean =
    accountEvent.kind == AccountEventType.Created && !accountEvents.exists(other =>
      other.kind == AccountEventType.Deleted && other.accountId == accountEvent.accountId
    )

  private[generators] def toFinalIdAgeAndCountry(data: (Long, Traversable[UserEvent])): (Long, Option[Int], Option[String]) =
    data match { case (id, events) =>
        events
          .filter(_.kind == UserEventType.Updated)
          .foldLeft(idAgeAndCountry(events.filter(_.kind == UserEventType.Created).head))((currentData, update) =>
            (currentData._1, update.personalData.age, update.contactData.country)
          )
    }

  private[generators] def idAgeAndCountry(event: UserEvent): (Long, Option[Int], Option[String]) =
    (event.id, event.personalData.age, event.contactData.country)

  private[generators] def notDeletedUsers(events: Traversable[UserEvent])(userEvent: UserEvent): Boolean =
    userEvent.kind == UserEventType.Created || userEvent.kind == UserEventType.Updated && events.exists(event =>
      event.kind == UserEventType.Deleted && event.id == userEvent.id
    )

  private[generators] def joinWith(accountEvents: Traversable[AccountEvent])
                                  (user: (Long, Option[Int], Option[String])): ((Long, Option[Int], Option[String]), Traversable[AccountEvent]) =
    (user, accountEvents.filter(_.userId == user._1).filter(createdAndNotDeleted(accountEvents)))

  private[generators] def joinWith(transactionEvents: Traversable[TransactionEvent])(account: UserAccount): UserAccount =
    transactionEvents.foldLeft(account){(accountData, event) => event match {
      case TransactionEvent(_, account.accountId, _, amount, TransactionEventType.Withdrawal) =>
        accountData.copy(balance = accountData.balance - amount)
      case TransactionEvent(_, accountData.accountId, _, amount, TransactionEventType.Insertion) =>
        accountData.copy(balance = accountData.balance + amount)
      case TransactionEvent(_, accountData.accountId, _, amount, TransactionEventType.Transfer) =>
        accountData.copy(balance = accountData.balance - amount)
      case TransactionEvent(_, _, Some(accountData.accountId), amount, TransactionEventType.Transfer) =>
        accountData.copy(balance = accountData.balance + amount)
      case other => accountData
    }}

  private[generators] def toUserAccounts(user: (Long, Option[Int], Option[String]),
                                         accountEvents: Traversable[AccountEvent]): Traversable[UserAccount] =
    accountEvents.map(event => UserAccount(user._1, event.accountId, event.balance, user._3, user._2))
}