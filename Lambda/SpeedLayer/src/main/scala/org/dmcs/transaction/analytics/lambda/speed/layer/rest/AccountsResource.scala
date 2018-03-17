package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.stream.Materializer

import scala.concurrent.ExecutionContext
import org.dmcs.transaction.analytics.lambda.speed.layer.actors.commands._

/**
  * Created by Zielony on 2016-08-01.
  */
private[rest] trait AccountsResource extends Directives with DefaultTimeout with StatisticalRestResponse {

  protected val accountsActor: ActorRef

  def accountsInterface(implicit actorSystem: ActorSystem, materializer: Materializer, executionContext: ExecutionContext): Route = {
    path("accounts" / "country" / Segment / "active") { country =>
      onSuccess(accountsActor ? CountAccountsByCountry(country))(respondWithLong)
    } ~ path("accounts" / "country" / Segment / "balance") { country =>
      ???
    }
  }
}