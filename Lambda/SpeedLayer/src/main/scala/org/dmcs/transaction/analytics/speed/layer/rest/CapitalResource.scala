package org.dmcs.transaction.analytics.speed.layer.rest

import java.time.LocalDateTime

import akka.actor.ActorRef
import akka.pattern._
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{AverageCapitalChangeInPeriod, AverageInsertionInPeriod, AverageWithdrawalInPeriod}
import spray.routing._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * A REST interface for the capital resource.
  */
trait CapitalResource extends HttpService {

  //TODO: Read from properties
  val defaultTimeout = 3 seconds

  val capitalActor: ActorRef

  /**
    * A REST interface for the capital resource.
    */
  def capitalInterface:Route = capitalVarianceInterface ~ averageInsertionInterface ~ averageWithdrawalInterface

  private def capitalVarianceInterface: Route = path("capital" / "variance") {
    parameters("start" ?, "end" ?) {
      withOptionalInterval { (start, end) =>
        val average = Await.result(capitalActor ? AverageCapitalChangeInPeriod(start, end), defaultTimeout)
        complete(average.toString)
      }
    }
  }

  private def averageInsertionInterface: Route = path("capital" / "insertions" / "average") {
    parameters("start" ?, "end" ? ) {
      withOptionalInterval {  (start, end) =>
        val average = Await.result(capitalActor ? AverageInsertionInPeriod(start, end), defaultTimeout)
        complete(average.toString)
      }
    }
  }

  private def averageWithdrawalInterface: Route = path("capital" / "withdrawals" / "average") {
    parameters("start" ? , "end" ?) {
      withOptionalInterval { (start, end) =>
        val average = Await.result(capitalActor ? AverageWithdrawalInPeriod(start, end), defaultTimeout)
        complete(average.toString)
      }
    }
  }

  /**
    * Builds a <code>Route</code> basing on
    * @param definition the <code>Route</code> building method.
    * @param start an optional, stringified date.
    * @param end
    * @return
    */
  private[rest] def withOptionalInterval(definition: ((Option[LocalDateTime], Option[LocalDateTime]) => Route))
                                  (start: Option[String], end: Option[String]): Route = {
    val startDate = start map parseTimestamp
    val endDate = end map parseTimestamp
    definition(startDate, endDate)
  }
}