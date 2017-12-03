package org.dmcs.transaction.analytics.classical.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}

private[classical] trait RestInterface extends AccountsResource with CapitalResource with ClientsResource {

  //TODO: Exception handler!
  def exposeRestInterface(host:String, port:Int)(implicit actorSystem:ActorSystem, materializer: Materializer,
  executionContext: ExecutionContext): Future[Http.ServerBinding] = {
    Http().bindAndHandle(handler = (clientsInterface ~ accountsInterface ~ capitalInterface), interface = host, port = port)
  }
}
