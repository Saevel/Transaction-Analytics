package org.dmcs.transaction.analytics.classical

import java.sql.Timestamp

import com.outworkers.phantom.dsl._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.inject._
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

package object dao {

  implicit val applicationProperties: Bundle = load(classpath :/ "application.properties")

  implicit class TimestampBuilder(dateTime: DateTime) {
    def toTimestamp: Timestamp = Timestamp.from(dateTime.toInstant.toDate.toInstant)
  }

  implicit class DateTimeBuilder(timestamp: Timestamp) {
    def toDateTime: DateTime = new DateTime(timestamp.toInstant)
  }

  private object Defaults {
    val connector = ContactPoint(%("cassandra.host"), %("cassandra.port")).keySpace(%("cassandra.keyspace"))
  }

  class DataBase(val keyspace: KeySpaceDef) extends Database[DataBase](keyspace) {

    object defaultUserDataDao extends UserDataDao with keyspace.Connector

    object defaultUserAccountDao extends UserAccountDao with keyspace.Connector

    object defaultCashOperationsDao extends CashOperationsDao with keyspace.Connector

    def create: Unit = {
      defaultCashOperationsDao.autocreate(space)
      defaultUserAccountDao.autocreate(space)
      defaultUserDataDao.autocreate(space)
    }
  }

  object DataAccessLayer extends DataBase(Defaults.connector)
}