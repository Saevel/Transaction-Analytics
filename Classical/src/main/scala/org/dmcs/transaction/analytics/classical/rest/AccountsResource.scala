package org.dmcs.transaction.analytics.classical.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern._

import org.dmcs.transaction.analytics.classical.services.UserAccountService
import org.dmcs.transaction.analytics.classical.dao._

import scala.concurrent.ExecutionContext

/**
  * Created by Zielony on 2016-08-01.
  */
private[rest] trait AccountsResource extends UserAccountService with DefaultTimeout {

  def accountsInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = {
    path("accounts" / "country" / Segment / "active") { country =>
      onSuccess(countAccountsByCountry(country).run(DataAccessLayer.userAccounts))(count =>
        complete(count.toString)
      )
    } ~ path("accounts" / "country" / Segment / "balance") { country =>
      onSuccess(averageAccountBalanceByCountry(country).run(DataAccessLayer.userAccounts))(count =>
        complete(count.toString)
      )
    }
  }
}