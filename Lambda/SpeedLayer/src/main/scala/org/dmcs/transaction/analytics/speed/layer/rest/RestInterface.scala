package org.dmcs.transaction.analytics.speed.layer.rest

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

import scala.concurrent.ExecutionContext

/**
  * Created by Zielony on 2016-08-01.
  */
private[layer] trait RestInterface extends SimpleRoutingApp
  with AccountsResource with CapitalResource with ClientsResource {

  def exposeRestInterface(host:String, port:Int)(implicit actorSystem:ActorSystem, executionContext: ExecutionContext): Unit = startServer(host, port) {
    clientsInterface ~ accountsInterface ~ capitalInterface
  }
}
