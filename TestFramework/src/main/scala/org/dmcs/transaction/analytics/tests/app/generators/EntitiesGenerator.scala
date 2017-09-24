package org.dmcs.transaction.analytics.tests.app.generators

import org.dmcs.transaction.analytics.tests.app.model.EntitiesModel
import org.scalacheck.Gen

trait EntitiesGenerator extends UserGenerator with AccountsGenerator with TransactionsGenerator {

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
