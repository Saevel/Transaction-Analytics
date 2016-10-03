package org.dmcs.transaction.analytics.speed.layer.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern._
import akka.util.Timeout
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}
import spray.routing.{HttpService, Route}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * A REST interface for the clients resource.
  */
private[rest] trait ClientsResource extends HttpService with DefaultTimeout {

  val clientsActor: ActorRef;

  /**
    * A REST interface for the clients resource.
    */
  def clientsInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = {
    path("clients" / "age" / "median") {
      get {
        onSuccess(clientsActor ? UserAgeAverage){ median =>
          complete(median.toString)
        }
      }
    } ~ path("clients" / "age" / "average") {
      get {
        onSuccess(clientsActor ? UserAgeMedian){ average =>
          complete(average.toString)
        }
      }
    }
  }
}