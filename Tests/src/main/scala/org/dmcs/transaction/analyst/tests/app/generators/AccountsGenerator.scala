package org.dmcs.transaction.analyst.tests.app.generators

import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-10-31.
  */
private[generators] trait AccountsGenerator {

  private[generators] def accountIdListGenerator(userCount: Long, accountsPerPerson: Double): Gen[List[Long]] = {
    val accountsCount = Math.ceil(userCount * accountsPerPerson).toLong
    Gen.const((0L to accountsCount).toList)
  }

  private[generators] def accountIdMapGenerator(userIds: List[Long], accountsPerPerson: Double): Gen[Map[Long, Long]] =
    accountIdListGenerator(userIds.size, accountsPerPerson).map( accounts =>
      Map(userIds.zip(accounts): _*)
    )

  private[generators] def accountGenerator(userIds: List[Long],
                                           accountIdsMap: Map[Long, Long],
                                           agesMap: Map[Long, Option[Int]],
                                           maximalInitialBalance: Double,
                                           countries: String*): Gen[List[UserAccount]] = {
    Gen.option(Gen.oneOf(countries)).flatMap( country =>
      Gen.choose(0.0, maximalInitialBalance).map(balance =>
        userIds.map { id =>
          val accountId = accountIdsMap.get(id).getOrElse(0L)
          val age = agesMap.get(id).getOrElse(None)
          UserAccount(id, accountId, balance, country, age)
        }
      )
    )
  }
}