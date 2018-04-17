package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, RequestContext, Route}
import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}


trait StatisticalRestResponse extends Directives {

  protected def respondWithInt(response: Any)(implicit system: ActorSystem, m: Materializer, ex: ExecutionContext): Route =
    response match {
      case expected: Int => complete(expected.toString)
      case unexpected => complete(StatusCodes.InternalServerError, s"Incorrect calculated statistic type. Value: ${unexpected}")
    }

  protected def respondWithDouble(response: Any)(implicit system: ActorSystem, m: Materializer, ex: ExecutionContext): Route =
    response match {
      case expected: Double => complete(expected.toString)
      case unexpected => complete(StatusCodes.InternalServerError, s"Incorrect calculated statistic type. Value: ${unexpected}")
  }

  protected def respondWithLong(response: Any)(implicit system: ActorSystem, m: Materializer, ex: ExecutionContext): Route =
    response match {
      case expected: Long => complete(expected.toString)
      case unexpected => complete(StatusCodes.InternalServerError, s"Incorrect calculated statistic type. Value: ${unexpected}")
  }
}