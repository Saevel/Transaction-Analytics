package org.dmcs.transaction.analytics.speed.layer.rest

import java.time.LocalDateTime

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern._
import akka.util.Timeout
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CapitalChangeInPeriod, AverageInsertionInPeriod, AverageWithdrawalInPeriod}
import spray.routing._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

/**
  * A REST interface for the capital resource.
  */
trait CapitalResource extends HttpService with DefaultTimeout {

  val capitalActor: ActorRef

  /**
    * A REST interface for the capital resource.
    */
  def capitalInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext):Route =
    capitalVarianceInterface ~ averageInsertionInterface ~ averageWithdrawalInterface

  private def capitalVarianceInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "variance") {
    parameters("start" ?, "end" ?) {
      withOptionalInterval { (start, end) =>
        onSuccess(capitalActor ? CapitalChangeInPeriod(start, end)) { average =>
          complete(average.toString)
        }
      }
    }
  }

  private def averageInsertionInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "insertions" / "average") {
    parameters("start" ?, "end" ? ) {
      withOptionalInterval {  (start, end) =>
        onSuccess(capitalActor ? AverageInsertionInPeriod(start, end)) { average =>
          complete(average.toString)
        }
      }
    }
  }

  private def averageWithdrawalInterface(implicit actorSystem: ActorSystem, executionContext: ExecutionContext): Route = path("capital" / "withdrawals" / "average") {
    parameters("start" ? , "end" ?) {
      withOptionalInterval { (start, end) =>
        onSuccess(capitalActor ? AverageWithdrawalInPeriod(start, end)){ average =>
          complete(average.toString)
        }
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