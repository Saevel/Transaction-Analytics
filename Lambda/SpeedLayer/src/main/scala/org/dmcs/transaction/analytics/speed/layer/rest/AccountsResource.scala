package org.dmcs.transaction.analytics.speed.layer.rest

import akka.actor.ActorRef
import akka.pattern._
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CountAccountsByCountry, CountAccountsByCountryAndAgeInterval}
import spray.routing.{HttpService, Route}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Zielony on 2016-08-01.
  */
private[rest] trait AccountsResource extends HttpService {

  val defaultTimeout = 3 second

  val accountsActor: ActorRef;

  def accountsInterface:Route = {
    path("accounts" / "country" / Segment / "active") { country =>
      val count = Await.result(accountsActor ? CountAccountsByCountry(country), defaultTimeout)
      complete(count.toString)
    } ~ path("accounts" / "country" / Segment / "balance") { country =>
      parameters(("minAge" ?).as[Option[Int]], ("maxAge" ?).as[Option[Int]]) { (minAge, maxAge) =>
        val count = Await.result(accountsActor ? CountAccountsByCountryAndAgeInterval(country, minAge, maxAge), defaultTimeout)
        complete(count.toString)
      }
    }
  }
}
