package org.dmcs.transaction.analytics.olap

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

  //TODO: Keyspace from .properties
  private object Defaults {
    val connector = ContactPoint.local.keySpace(%("cassandra.keyspace"))
  }

  private class DataBase(val keyspace: KeySpaceDef) extends Database(keyspace) {

    val defaultUserDataDao = new UserDataDao with keyspace.Connector

    val defaultUserAccountDao = new UserAccountDao with keyspace.Connector

    val defaultCashOperationsDao = new CashOperationsDao with keyspace.Connector

    //TODO: KEYSPACE?
    def create: Unit = {
      /*
      defaultCashOperationsDao.autocreate(???)
      defaultUserAccountDao.autocreate(???)
      defaultUserDataDao.autocreate(???)
      */
    }
  }

  private[olap] object DataAccessLayer extends DataBase(Defaults.connector)
}
