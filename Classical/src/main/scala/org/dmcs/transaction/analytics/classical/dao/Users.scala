package org.dmcs.transaction.analytics.classical.dao

import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.column.{OptionalPrimitiveColumn, PrimitiveColumn}
import com.outworkers.phantom.dsl._
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.lambda.events._

import scala.concurrent.Future

class Users extends CassandraTable[UserDataDao, UserData]{

  object userId extends LongColumn(this) with PartitionKey

  object age extends IntColumn(this)

  override def fromRow(row: Row): UserData = UserData(userId(row), age(row))
}

abstract class UserDataDao extends Users with RootConnector {

  def findAll: Future[List[UserData]] = select.fetch
}