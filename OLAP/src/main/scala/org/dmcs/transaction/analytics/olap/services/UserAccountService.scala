package org.dmcs.transaction.analytics.olap.services

import cats.data.Reader
import org.dmcs.transaction.analytics.olap.dao.UserAccountDao

import scala.concurrent.Future}

import scala.concurrent.ExecutionContext

trait UserAccountService {

  private[olap] def countAccountsByCountry(country: String)(implicit executionContext: ExecutionContext): Reader[UserAccountDao, Future[Int]] =
    Reader(dao => dao.findByCountry(country).map(_.length))

  private[olap] def countAccountsByCountryAndAgeInterval(country: String, minAge: Option[Int], maxAge: Option[Int])
                                          (implicit executionContext: ExecutionContext): Reader[UserAccountDao, Future[Int]] =
    Reader(dao =>
      if(minAge.isDefined && maxAge.isDefined) {
        dao.findByCountryAndAgeInterval(country, minAge, maxAge).map(_.length)
      }
      else if(minAge isDefined) {
        dao.findByCountryAndMinAge(country, minAge).map(_.length)
      }
      else if(maxAge isDefined) {
        dao.findByCountryAndMaxAge(country, maxAge).map(_.length)
      }
      else {
        dao.findByCountry(country).map(_.length)
      }
    )

  private[olap] def averageAccountBalanceByCountry(country: String)(implicit executionContext: ExecutionContext): Reader[UserAccountDao, Future[Double]] =
    Reader( dao =>
      dao.findByCountry(country).map( users =>
        users.reduce((first, second) => (first.balance + second.balance) / users.length)
      )
    )
}
