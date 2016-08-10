package org.dmcs.transaction.analytics.speed.layer

import akka.actor.{ActorRef, ActorSystem, Props}
import org.dmcs.transaction.analytics.speed.layer.actors.{AccountsActor, CapitalActor, ClientsActor}
import org.dmcs.transaction.analytics.speed.layer.rest.RestInterface

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends App with RestInterface {

  implicit val actorSystem: ActorSystem = ActorSystem("SpeedLayer")

  override val capitalActor: ActorRef = actorSystem.actorOf(Props[CapitalActor])

  override val accountsActor: ActorRef = actorSystem.actorOf(Props[AccountsActor])

  override val clientsActor: ActorRef = actorSystem.actorOf(Props[ClientsActor])

  //TODO: Host & port from properties
  exposeRestInterface("localhost", 9990)
}
