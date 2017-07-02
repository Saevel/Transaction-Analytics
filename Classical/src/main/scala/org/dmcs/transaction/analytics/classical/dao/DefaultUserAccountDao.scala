package org.dmcs.transaction.analytics.classical.dao

import java.sql.Timestamp

import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.keys.Index
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, AccountEventType}

import scala.concurrent.Future

//TODO: Should store UserAccounts not events?
class DefaultUserAccountDao extends CassandraTable[UserAccountDao, UserAccount]{

  object userId extends LongColumn(this)

  object accountId extends LongColumn(this) with PartitionKey

  object balance extends DoubleColumn(this)

  object age extends OptionalIntColumn(this) with Index

  object country extends OptionalStringColumn(this) with Index
}

abstract class UserAccountDao extends DefaultUserAccountDao with RootConnector {

  def findByCountry(country: String): Future[List[UserAccount]] =
    select.where(_.country eqs country).fetch

  def findByCountryAndMinAge(country: String, minAge: Option[Int]): Future[List[UserAccount]] =
    select.where(_.country eqs country).and(_.age >= minAge).fetch

  def findByCountryAndMaxAge(country: String, maxAge: Option[Int]): Future[List[UserAccount]] =
    select.where(_.country eqs country).and(_.age <= maxAge).fetch

  def findByCountryAndAgeInterval(country: String, minAge: Option[Int], maxAge: Option[Int]): Future[List[UserAccount]] =
    select.where(_.country eqs country).and(_.age >= minAge).and(_.age <= maxAge).fetch
}

