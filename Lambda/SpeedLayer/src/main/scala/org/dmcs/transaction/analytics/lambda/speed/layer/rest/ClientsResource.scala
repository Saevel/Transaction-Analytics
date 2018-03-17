package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.stream.Materializer
import org.dmcs.transaction.analytics.lambda.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}

import scala.concurrent.ExecutionContext

/**
  * A REST interface for the clients resource.
  */
private[rest] trait ClientsResource extends Directives with StatisticalRestResponse with DefaultTimeout {

  protected val clientsActor: ActorRef

  /**
    * A REST interface for the clients resource.
    */
  def clientsInterface(implicit actorSystem: ActorSystem, m: Materializer,  executionContext: ExecutionContext): Route = {
    path("clients" / "age" / "median") {
      get {
        onSuccess(clientsActor ? UserAgeMedian)(respondWithInt)
      }
    } ~ path("clients" / "age" / "average") {
      get {
        onSuccess(clientsActor ? UserAgeAverage)(respondWithDouble)
      }
    }
  }
}