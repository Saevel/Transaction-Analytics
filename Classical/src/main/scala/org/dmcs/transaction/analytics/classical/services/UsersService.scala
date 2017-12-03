package org.dmcs.transaction.analytics.classical.services

import cats.data.Reader
import org.dmcs.transaction.analytics.lambda.events.UserEvent
import org.dmcs.transaction.analytics.classical.dao.UserDataDao

import scala.concurrent.{ExecutionContext, Future}

trait UsersService {

  private[classical] def averageUserAge(implicit executionContext: ExecutionContext): Reader[UserDataDao, Future[Double]] =
    Reader( dao => dao.findAll.map(users =>
      users
        .map(_.age)
        .foldLeft(0.0)((first, second) => (first + second) / users.length)
    ))

  private[classical] def userAgeMedian(implicit executionContext: ExecutionContext): Reader[UserDataDao, Future[Int]] = Reader (dao =>
    dao.findAll.map(users =>
      users
        .map(_.age)
        .sorted.applyOrElse(Math.floorDiv(users.length, 2), {_: Int => 0})
    )
  )
}