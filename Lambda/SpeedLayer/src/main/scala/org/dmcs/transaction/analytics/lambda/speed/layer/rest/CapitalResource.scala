package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import java.time.LocalDateTime

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.stream.Materializer
import org.dmcs.transaction.analytics.lambda.speed.layer.actors.commands.{AverageInsertionInPeriod, AverageWithdrawalInPeriod, CapitalChangeInPeriod}

import scala.concurrent.ExecutionContext

/**
  * A REST interface for the capital resource.
  */
trait CapitalResource extends Directives with StatisticalRestResponse with DefaultTimeout {

  protected val capitalActor: ActorRef

  /**
    * A REST interface for the capital resource.
    */
  def capitalInterface(implicit actorSystem: ActorSystem, m: Materializer,  executionContext: ExecutionContext): Route =
    capitalVarianceInterface ~ averageInsertionInterface ~ averageWithdrawalInterface

  private def capitalVarianceInterface(implicit actorSystem: ActorSystem, m: Materializer, executionContext: ExecutionContext): Route =
    path("capital" / "variance") {
      parameters("start" ?, "end" ?) {
        withOptionalInterval { (start, end) =>
          onSuccess(capitalActor ? CapitalChangeInPeriod(start, end))(respondWithDouble)
        }
      }
    }

  private def averageInsertionInterface(implicit actorSystem: ActorSystem, m: Materializer, executionContext: ExecutionContext): Route =
    path("capital" / "insertions" / "average") {
      parameters("start" ?, "end" ? ) {
        withOptionalInterval {  (start, end) =>
          onSuccess(capitalActor ? AverageInsertionInPeriod(start, end))(respondWithDouble)
        }
      }
    }

  private def averageWithdrawalInterface(implicit actorSystem: ActorSystem, m: Materializer, executionContext: ExecutionContext): Route =
    path("capital" / "withdrawals" / "average") {
      parameters("start" ? , "end" ?) {
        withOptionalInterval { (start, end) =>
          onSuccess(capitalActor ? AverageWithdrawalInPeriod(start, end))(respondWithDouble)
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