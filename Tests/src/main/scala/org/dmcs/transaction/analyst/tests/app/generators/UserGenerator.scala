package org.dmcs.transaction.analyst.tests.app.generators

import org.dmcs.transaction.analyst.lambda.model.UserData
import org.scalacheck.Gen

/**
  * Created by Zielony on 2016-10-31.
  */
private[generators] trait UserGenerator {

  private[generators] def idListGenerator(size: Long): Gen[List[Long]] = Gen.const((0L to size).toList)

  private[generators] def agesGenerator(userCount: Long): Gen[List[Option[Int]]] =
    Gen.listOfN(userCount.toInt, Gen.option(Gen.choose(0, 99)))

  private[generators] def agesMapGenerator(userIds: List[Long]): Gen[Map[Long, Option[Int]]] = agesGenerator(userIds.size).map( ages =>
    Map(userIds.zip(ages): _*)
  )

  private[generators] def userDataGenerator(userIds: List[Long], ageMap: Map[Long, Option[Int]]): Gen[List[UserData]] = for {
    id <- userIds
    ageOpt <- ageMap.get(id)
    age <- ageOpt
  } yield(UserData(id, age))
}
