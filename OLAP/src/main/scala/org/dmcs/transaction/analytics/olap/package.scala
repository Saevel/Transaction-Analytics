package org.dmcs.transaction.analytics

import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import StatusCodes._

/**
  * Created by Zielony on 2017-01-24.
  */
package object olap {
  //TODO: More cases
  implicit val exceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: RuntimeException => complete(
      HttpResponse(InternalServerError, entity = "An iinternal server exception occured. Please contact application support")
    )
    case e: Exception => complete(
      HttpResponse(BadRequest, entity = "Bad request")
    )

  }

}
