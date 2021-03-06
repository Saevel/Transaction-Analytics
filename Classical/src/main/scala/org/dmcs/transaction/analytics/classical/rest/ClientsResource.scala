package org.dmcs.transaction.analytics.classical.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern._
import org.dmcs.transaction.analytics.classical.services.UsersService
import org.dmcs.transaction.analytics.classical.dao._

import scala.concurrent.ExecutionContext

/**
  * A REST interface for the clients resource.
  */
private[rest] trait ClientsResource extends UsersService with DefaultTimeout {

  /**
    * A REST interface for the clients resource.
    */
  def clientsInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = {
    path("clients" / "age" / "median") {
      get {
        onSuccess(userAgeMedian.run(DataAccessLayer.userData))(median =>
          complete(median.toString)
        )
      }
    } ~ path("clients" / "age" / "average") {
      get {
        onSuccess(averageUserAge.run(DataAccessLayer.userData))(averageAge =>
          complete(averageAge.toString)
        )
      }
    }
  }
}