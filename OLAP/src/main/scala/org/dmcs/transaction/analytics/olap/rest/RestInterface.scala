package org.dmcs.transaction.analytics.olap.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer

import scala.concurrent.ExecutionContext

private[olap] trait RestInterface extends AccountsResource with CapitalResource with ClientsResource {

  //TODO: Exception handler!
  def exposeRestInterface(host:String, port:Int)(implicit actorSystem:ActorSystem, materializer: Materializer,
  executionContext: ExecutionContext): Unit = {
    Http().bindAndHandle(handler = (clientsInterface ~ accountsInterface ~ capitalInterface), interface = host, port = port)
  }
}
