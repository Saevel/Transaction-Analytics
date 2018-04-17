package org.dmcs.transaction.analytics.tests.generators.events.transactions

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, TransactionEvent, TransactionEventType}
import org.dmcs.transaction.analytics.tests.generators.TransactionsGeneratorConfiguration
import org.scalacheck.Gen

trait TransactionEventsGenerator {

  protected def transactionEventsGenerator(configuration: TransactionsGeneratorConfiguration,
                                               accountCreationEvents: Traversable[AccountEvent]): Gen[List[TransactionEvent]] =
    for {
      widthrawals <- withdrawalEventsGenerator(configuration.withdrawalsPerPhase.toInt, accountCreationEvents)
      insertions <- insertionEventsGenerator(configuration.insertionsPerPhase.toInt, accountCreationEvents)
      transfers <- transferEventsGenerator(configuration.transfersPerPhase.toInt, accountCreationEvents)
    } yield widthrawals ++ insertions ++ transfers

  private[generators] def withdrawalEventsGenerator(withdrawalsPerPhase: Int, accounts: Traversable[AccountEvent]): Gen[List[TransactionEvent]] =
    Gen.listOfN(withdrawalsPerPhase, for {
      account <- Gen.oneOf(accounts.toSeq)
      amount <- Gen.choose(0.0, account.balance)
    } yield TransactionEvent(Timestamp.valueOf(LocalDateTime.now), account.accountId, None, amount, TransactionEventType.Withdrawal))

  private[generators] def insertionEventsGenerator(insertionsPerPhase: Int, accounts: Traversable[AccountEvent]): Gen[List[TransactionEvent]] =
    Gen.listOfN(insertionsPerPhase, for {
      account <- Gen.oneOf(accounts.toSeq)
      amount <- Gen.choose(0.0, account.balance)
    } yield TransactionEvent(Timestamp.valueOf(LocalDateTime.now), account.accountId, None, amount, TransactionEventType.Insertion))

  private[generators] def transferEventsGenerator(transfersPerPhase: Int, accounts: Traversable[AccountEvent]): Gen[List[TransactionEvent]] =
    Gen.listOfN(transfersPerPhase, for {
      sourceAccount <- Gen.oneOf(accounts.toSeq)
      targetAccount <- Gen.oneOf(accounts.toSeq)
      amount <- Gen.choose(0.0, sourceAccount.balance)
    } yield TransactionEvent(Timestamp.valueOf(LocalDateTime.now), sourceAccount.accountId, Option(targetAccount.accountId), amount, TransactionEventType.Transfer))
}