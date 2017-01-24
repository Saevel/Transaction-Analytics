package org.dmcs.transaction.analytics.olap.rest

import java.time.LocalDateTime

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern._

import scala.concurrent.ExecutionContext
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.dmcs.transaction.analyst.lambda.model.CashOperationType
import org.dmcs.transaction.analytics.olap.services.CapitalService
import org.dmcs.transaction.analytics.olap.dao._

/**
  * A REST interface for the capital resource.
  */
trait CapitalResource extends CapitalService with DefaultTimeout {

  /**
    * A REST interface for the capital resource.
    */
  def capitalInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route =
    capitalVarianceInterface ~ averageInsertionInterface ~ averageWithdrawalInterface

  private def capitalVarianceInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "variance") {
    parameters("start" ?, "end" ?) {
      withOptionalInterval { (start, end) =>
        onSuccess(averageCapitalVariance(start, end).run(DataAccessLayer.defaultCashOperationsDao))( variance =>
          complete(variance.toString)
        )
      }
    }
  }

  private def averageInsertionInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "insertions" / "average") {
    parameters("start" ?, "end" ? ) {
      withOptionalInterval {  (start, end) =>
        onSuccess(averageOperationValue(CashOperationType.Insertion, start, end).run(DataAccessLayer.defaultCashOperationsDao))( average =>
          complete(average.toString)
        )
      }
    }
  }

  private def averageWithdrawalInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "withdrawals" / "average") {
    parameters("start" ? , "end" ?) {
      withOptionalInterval { (start, end) =>
        onSuccess(averageOperationValue(CashOperationType.Withdrawal, start, end).run(DataAccessLayer.defaultCashOperationsDao))( average =>
          complete(average.toString)
        )
      }
    }
  }

  /**
    * Builds a <code>Route</code> basing on
 *
    * @param definition the <code>Route</code> building method.
    * @param start an optional, stringified date.
    * @param end
    * @return
    */
  private[rest] def withOptionalInterval(definition: ((Option[LocalDateTime], Option[LocalDateTime]) => Route))
                                  (start: Option[String], end: Option[String])
                                  (implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = {
    val startDate = start map parseTimestamp
    val endDate = end map parseTimestamp
    definition(startDate, endDate)
  }
}