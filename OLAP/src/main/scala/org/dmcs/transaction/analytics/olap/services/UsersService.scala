package org.dmcs.transaction.analytics.olap.services

import cats.data.Reader
import org.dmcs.transaction.analytics.lambda.events.UserEvent
import org.dmcs.transaction.analytics.olap.dao.UserDataDao

import scala.concurrent.{ExecutionContext, Future}

//TODO: OBJECT OR TRAIT?
trait UsersService {

  private[olap] def averageUserAge(implicit executionContext: ExecutionContext): Reader[UserDataDao, Future[Double]] =
    Reader( dao => dao.findAll.map(users =>
      users
        .map(_.age)
        .reduce((first, second) => (first + second) / users.length)
    ))

  private[olap] def userAgeMedian(implicit executionContext: ExecutionContext): Reader[UserDataDao, Future[Int]] = Reader (dao =>
    dao.findAll.map(users =>
      users
        .map(_.age)
        .sorted
        .apply(Math.floorDiv(users.length, 2))
    )
  )
}
