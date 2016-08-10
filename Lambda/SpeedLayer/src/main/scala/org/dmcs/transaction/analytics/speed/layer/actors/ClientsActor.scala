package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}
import org.dmcs.transaction.analytics.speed.layer.adapters.ClientsAdapter
import org.dmcs.transaction.analytics.speed.layer.queries.ClientsQueries

/**
  * Created by Zielony on 2016-08-03.
  */
class ClientsActor extends Actor with ClientsAdapter with ClientsQueries {

  override def receive: Receive = {

    case UserAgeAverage => sender ! withClientData(averageClientAge)

    case UserAgeMedian => sender ! withClientData(clientAgeMedian)
  }
}
