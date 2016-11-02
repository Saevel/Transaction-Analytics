package org.dmcs.transaction.analyst.tests.app.generators

import java.sql.Timestamp

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType, UserAccount, UserData}
import org.dmcs.transaction.analyst.tests.app.model.{EntitiesModel, EventsModel, TestModel}
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-10-31.
  */
/*private[generators]*/ trait EntitiesGenerator extends UserGenerator with AccountsGenerator with TransactionsGenerator {

  def entitiesGenerator(userCount: Long,
                        accountsPerPerson: Double,
                        transactionsPerAccount: Double,
                        transactionSpread: Double,
                        maximalInitialBalance: Double,
                        countries: String*): Gen[EntitiesModel] = for {
    userIds <- idListGenerator(userCount)
    accountIdsMap <- accountIdMapGenerator(userIds, accountsPerPerson)
    agesMap <- agesMapGenerator(userIds)
    userData <- userDataGenerator(userIds, agesMap)
    originalAccounts <- accountGenerator(userIds, accountIdsMap, agesMap, maximalInitialBalance, countries: _*)
    (newAccounts, transactions) <- transactionsGenerator(transactionsPerAccount, originalAccounts, transactionSpread)
  } yield EntitiesModel(userData, newAccounts, transactions, originalAccounts)
}