package org.dmcs.transaction.analytics.speed.layer.rest

import java.time.LocalDateTime

import akka.actor.ActorRef
import akka.pattern._
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{UserAgeAverage, UserAgeMedian}
import spray.routing.{HttpService, Route}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * A REST interface for the clients resource.
  */
private[rest] trait ClientsResource extends HttpService {

  val defaultTimeout = 3 seconds

  val clientsActor: ActorRef;

  /**
    * A REST interface for the clients resource.
    */
  def clientsInterface: Route = {
    path("clients" / "age" / "median") {
      get {
        val median = Await.result(clientsActor ? UserAgeAverage, defaultTimeout)
        complete(median.toString)
      }
    } ~ path("clients" / "age" / "average") {
      get {
        val average = Await.result(clientsActor ? UserAgeMedian, defaultTimeout)
        complete(average.toString)
      }
    }
  }
}