package org.dmcs.transaction.analytics.olap.rest

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.pattern._

import org.dmcs.transaction.analytics.olap.services.UserAccountService
import org.dmcs.transaction.analytics.olap.dao._

import scala.concurrent.ExecutionContext

/**
  * Created by Zielony on 2016-08-01.
  */
private[rest] trait AccountsResource extends UserAccountService with DefaultTimeout {

  def accountsInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = {
    path("accounts" / "country" / Segment / "active") { country =>
      onSuccess(countAccountsByCountry(country).run(DataAccessLayer.defaultUserAccountDao))(count =>
        complete(count.toString)
      )
    } ~ path("accounts" / "country" / Segment / "balance") { country =>
      onSuccess(averageAccountBalanceByCountry(country).run(DataAccessLayer.defaultUserAccountDao))( count =>
        complete(count.toString)
      )
    }
  }
}