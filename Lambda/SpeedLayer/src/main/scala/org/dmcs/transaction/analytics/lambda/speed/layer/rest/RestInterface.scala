package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}

private[layer] trait RestInterface extends AccountsResource with CapitalResource with ClientsResource {

  //TODO: Exception handler!
  def exposeRestInterface(host:String, port:Int)(implicit actorSystem:ActorSystem, materializer: Materializer,
  executionContext: ExecutionContext): Future[Http.ServerBinding] = {
    Http().bindAndHandle(handler = (clientsInterface ~ accountsInterface ~ capitalInterface), interface = host, port = port)
  }
}