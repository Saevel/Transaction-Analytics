package org.dmcs.transaction.analyst.tests.app.generators

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType, UserAccount}
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-10-31.
  */
private[generators] trait TransactionsGenerator {

  private[generators] def operationsGenerator(transactionsPerAccount: Double,
                                              originalAccounts: List[UserAccount],
                                              transactionSpread: Double): Gen[List[CashOperation]] = {
    val accountIds = originalAccounts.map(_.accountId)
    Gen.listOfN(Math.ceil(accountIds.size * transactionsPerAccount).toInt, for {
      sourceAccount <- Gen.oneOf(accountIds)
      targetAccount <- Gen.oneOf(accountIds)
    //TODO: Implement such generator
      timestamp <- Gen.const(Timestamp.valueOf(LocalDateTime.now))
      amount <- Gen.choose(0.0, transactionSpread)
      kind <- Gen.oneOf(CashOperationType.Insertion, CashOperationType.Withdrawal, CashOperationType.Transfer)
    } yield CashOperation(
      timestamp,
      sourceAccount,
      if(kind == CashOperationType.Transfer) Option(targetAccount) else None,
      kind,
      amount
    ))
  }

  private[generators] def updatedAccountsGenerator(originalAccounts: List[UserAccount], cashOperations: List[CashOperation]): Gen[List[UserAccount]] = originalAccounts.map { account =>
    val in = cashOperations.filter(operation => (operation.kind == CashOperationType.Insertion &&
      operation.sourceAccountId == account.accountId) || (operation.kind == CashOperationType.Transfer &&
      operation.targetAccountId == Some(account.accountId)))

    val out = cashOperations.filter(operation => (operation.kind == CashOperationType.Withdrawal &&
      operation.sourceAccountId == account.accountId) || (operation.kind == CashOperationType.Transfer &&
      operation.sourceAccountId == account.accountId))

    val delta = in.map(_.amount).sum - out.map(_.amount).sum

    account.copy(balance = account.balance + delta)
  }

  private[generators] def transactionsGenerator(transactionsPerAccount: Double,
                                                originalAccounts: List[UserAccount],
                                                transactionSpread: Double): Gen[(List[UserAccount], List[CashOperation])] = for {
    cashOperations <- operationsGenerator(transactionsPerAccount, originalAccounts, transactionSpread)
    newAccounts <- updatedAccountsGenerator(originalAccounts, cashOperations)
  } yield (newAccounts, cashOperations)
}
