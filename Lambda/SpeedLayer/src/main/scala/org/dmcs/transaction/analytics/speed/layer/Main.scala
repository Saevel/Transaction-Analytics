package org.dmcs.transaction.analytics.speed.layer

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import org.dmcs.transaction.analytics.speed.layer.actors.{AccountsActor, CapitalActor, ClientsActor}
import org.dmcs.transaction.analytics.speed.layer.rest.RestInterface

import scala.concurrent.duration._

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends App with RestInterface {

  import scala.concurrent.ExecutionContext.Implicits._

  implicit val actorSystem: ActorSystem = ActorSystem("SpeedLayer")

  override val capitalActor: ActorRef = actorSystem.actorOf(Props[CapitalActor])

  override val accountsActor: ActorRef = actorSystem.actorOf(Props[AccountsActor])

  override val clientsActor: ActorRef = actorSystem.actorOf(Props[ClientsActor])

  //TODO: From properties
  override implicit val defaultTimeout: Timeout = 3 seconds

  //TODO: Port from properties
  exposeRestInterface("localhost", 9990)
}