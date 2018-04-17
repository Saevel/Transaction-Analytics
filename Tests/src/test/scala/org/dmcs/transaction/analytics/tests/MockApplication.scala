package org.dmcs.transaction.analytics.tests

import scala.concurrent.ExecutionContext.Implicits.global

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{complete, get, onSuccess, parameters, path}
import akka.stream.{ActorMaterializer, Materializer}
import org.dmcs.transaction.analyst.lambda.model.CashOperationType

import scala.util.{Failure, Success}

object MockApplication extends App with Directives {

  implicit val actorSystem = ActorSystem("MockApplication")

  implicit val materializer: Materializer = ActorMaterializer()

  Http().bindAndHandle(interface = "127.0.0.1", port = 8080, handler = path("clients" / "age" / "median") {
    get(complete("0"))
  } ~ path("clients" / "age" / "average") {
    get(complete("0.0"))
  } ~ path("accounts" / "country" / Segment / "active") { _ =>
    complete("0")
  } ~ path("accounts" / "country" / Segment / "balance") { _ =>
    complete("0.0")
  } ~ path("capital" / "variance"){ parameters("start" ?, "end" ?){ (_, _) =>
    complete("0.0")
  }} ~ path("capital" / "insertions" / "average"){ parameters("start" ?, "end" ? ){ (_, _) =>
    complete("0.0")
  }} ~ path("capital" / "withdrawals" / "average"){ parameters("start" ? , "end" ?){ (_, _) =>
    complete("0.0")
  }}) onComplete {
    case Success(binding) => println(s"Mock HTTP running at: localhost:8080")
    case Failure(e) => e.printStackTrace
  }
}
