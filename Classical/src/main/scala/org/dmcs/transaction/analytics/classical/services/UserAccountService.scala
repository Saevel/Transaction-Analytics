package org.dmcs.transaction.analytics.classical.services

import cats.data.Reader
import org.dmcs.transaction.analytics.classical.dao.UserAccountDao

import scala.concurrent.Future

import scala.concurrent.ExecutionContext

trait UserAccountService {

  private[classical] def countAccountsByCountry(country: String)(implicit executionContext: ExecutionContext): Reader[UserAccountDao, Future[Int]] =
    Reader(dao => dao.findByCountry(country).map(_.length))

  private[classical] def countAccountsByCountryAndAgeInterval(country: String, minAge: Option[Int], maxAge: Option[Int])
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

  private[classical] def averageAccountBalanceByCountry(country: String)(implicit executionContext: ExecutionContext): Reader[UserAccountDao, Future[Double]] =
    Reader( dao =>
      dao.findByCountry(country).map( users =>
        users.map(_.balance).foldLeft(0.0)((first, second) => (first + second) / users.length)
      )
    )
}