package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}
import org.dmcs.transaction.analytics.speed.layer.adapters.ClientsAdapter
import org.dmcs.transaction.analytics.speed.layer.queries.ClientsQueries

/**
  * Created by Zielony on 2016-08-03.
  */
class ClientsActor(private val clientsLocation: String, private val sqlContext: SQLContext)
  extends Actor with ClientsAdapter with ClientsQueries {

  implicit val sql: SQLContext = sqlContext

  override val clientsPath: String = clientsLocation

  override def receive: Receive = {

    case UserAgeAverage => sender ! withClientData(averageClientAge)

    case UserAgeMedian => sender ! withClientData(clientAgeMedian)
  }
}
