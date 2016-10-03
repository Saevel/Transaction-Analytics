package org.dmcs.transaction.analytics.speed.layer.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern._
import akka.util.Timeout
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CountAccountsByCountry, CountAccountsByCountryAndAgeInterval}
import spray.routing.{HttpService, Route}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * Created by Zielony on 2016-08-01.
  */
private[rest] trait AccountsResource extends HttpService with DefaultTimeout {

  val accountsActor: ActorRef;

  def accountsInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext):Route = {
    path("accounts" / "country" / Segment / "active") { country =>
      onSuccess(accountsActor ? CountAccountsByCountry(country)){ count =>
        complete(count.toString)
      }
    } ~ path("accounts" / "country" / Segment / "balance") { country =>
      parameters(("minAge" ?).as[Option[Int]], ("maxAge" ?).as[Option[Int]]) { (minAge, maxAge) =>
        onSuccess(accountsActor ? CountAccountsByCountryAndAgeInterval(country, minAge, maxAge)){ count =>
          complete(count.toString)
        }
      }
    }
  }
}