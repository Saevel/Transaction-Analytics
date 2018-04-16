package org.dmcs.transaction.analytics.tests.generators.events

import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType, UserEvent, UserEventType}
import org.dmcs.transaction.analytics.tests.generators.ApplicationModelGenerator.{accountEventsGenerator, transactionEventsGenerator, userEventsGenerator}
import org.dmcs.transaction.analytics.tests.generators.GeneratorConfiguration
import org.dmcs.transaction.analytics.tests.generators.events.accounts.AccountEventsGenerator
import org.dmcs.transaction.analytics.tests.generators.events.transactions.TransactionEventsGenerator
import org.dmcs.transaction.analytics.tests.generators.events.users.UserEventsGenerator
import org.dmcs.transaction.analytics.tests.model.EventsModel
import org.scalacheck.Gen

trait EventsGenerator extends AccountEventsGenerator with TransactionEventsGenerator with UserEventsGenerator {

  protected def eventsGenerator(configuration: GeneratorConfiguration): Gen[EventsModel] = for {
    userEvents <- userEventsGenerator(configuration.users)
    accountEvents <- accountEventsGenerator(configuration.accounts, usersCreatedButNotDeleted(userEvents))
    transactionEvents <- transactionEventsGenerator(configuration.transactions, accountsCreatedButNotDeleted(accountEvents))
  } yield EventsModel(accountEvents, transactionEvents, userEvents)

  private[events] def usersCreatedButNotDeleted(events: Traversable[UserEvent]): Traversable[UserEvent] =
    events.filter(_.kind == UserEventType.Created).filter(creationEvent =>
      !events.filter(_.kind == UserEventType.Deleted).exists(_.id == creationEvent.id)
    )

  private[events] def accountsCreatedButNotDeleted(events: Traversable[AccountEvent]): Traversable[AccountEvent] =
    events.filter(_.kind == AccountEventType.Created).filter(creationEvent =>
      !events.filter(_.kind == AccountEventType.Deleted).exists(_.accountId == creationEvent.accountId)
    )
}