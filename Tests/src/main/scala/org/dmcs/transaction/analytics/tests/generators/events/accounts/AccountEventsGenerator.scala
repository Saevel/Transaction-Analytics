package org.dmcs.transaction.analytics.tests.generators.events.accounts

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType, UserEvent}
import org.dmcs.transaction.analytics.tests.generators.AccountsGeneratorConfiguration
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

trait AccountEventsGenerator {

  protected def accountEventsGenerator(configuration: AccountsGeneratorConfiguration, userCreationEvents: Traversable[UserEvent]): Gen[List[AccountEvent]] =
    for {
      creationEvents <- accountsCreatedGenerator(configuration, userCreationEvents)
      deletionEvents <- accountsDeletedGenerator(configuration, creationEvents)
    } yield creationEvents ++ deletionEvents

  private[accounts] def accountsCreatedGenerator(configuration: AccountsGeneratorConfiguration, userCreationEvents: Traversable[UserEvent]): Gen[List[AccountEvent]] =
    Gen.listOfN(Math.floor(configuration.accountsPerUser * userCreationEvents.size).toInt, for {
      accountId <- arbitrary[Long].map(Math.abs)
      balance <- Gen.choose(configuration.minimalInitialBalance, configuration.maximalInitialBalance)
      creationEvent <- Gen.oneOf(userCreationEvents.toSeq)
    } yield AccountEvent(Timestamp.valueOf(LocalDateTime.now), creationEvent.id, accountId, balance, AccountEventType.Created))

  private[accounts] def accountsDeletedGenerator(configuration: AccountsGeneratorConfiguration, creationEvents: List[AccountEvent]): Gen[List[AccountEvent]] =
    Gen.listOfN(Math.floor(configuration.deletedAccountsPercentage * creationEvents.size).toInt, Gen.oneOf(creationEvents))
      .map(events => events.map(event => event.copy(timestamp = Timestamp.valueOf(LocalDateTime.now), kind = AccountEventType.Deleted)))
}