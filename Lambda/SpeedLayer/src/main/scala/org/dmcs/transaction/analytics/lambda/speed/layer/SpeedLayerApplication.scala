package org.dmcs.transaction.analytics.lambda.speed.layer

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.Timeout
import org.dmcs.transaction.analytics.lambda.speed.layer.actors.{AccountsActor, CapitalActor, ClientsActor}
import org.dmcs.transaction.analytics.lambda.speed.layer.rest.RestInterface
import org.dmcs.transaction.analytics.lambda.speed.layer.spark.Spark

import scala.concurrent.duration._
import prv.zielony.proper.inject._
import prv.zielony.proper.converters._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.util.{Failure, Success}

/**
  * Created by Zielony on 2016-08-01.
  */
object SpeedLayerApplication extends App with Spark {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val actorSystem: ActorSystem = ActorSystem("SpeedLayer")

  implicit val materializer: Materializer = ActorMaterializer()

  val batchProperties = load(classpath :/ "batch.properties")
  val speedProperties = load(classpath :/ "speed.properties")

  val accountsPath = %("batch.views.accounts" @@ batchProperties)
  val clientsPath = %("batch.views.users" @@ batchProperties)
  val capitalPath = %("batch.views.cash.operations" @@ batchProperties)

  withSpark { implicit sparkContext =>
    withSparkSql { implicit sqlContext =>

      val rest = new RestInterface {

        override implicit val defaultTimeout: Timeout = 3 seconds

        override val clientsActor: ActorRef = actorSystem.actorOf(Props(new ClientsActor(clientsPath, sqlContext)))
        override val accountsActor: ActorRef = actorSystem.actorOf(Props(new AccountsActor(accountsPath, sqlContext)))
        override val capitalActor: ActorRef = actorSystem.actorOf(Props(new CapitalActor(capitalPath, sqlContext)))
      }

      rest.exposeRestInterface("localhost", int(%("http.interface.port" @@ speedProperties))) onComplete{
        case Success(binding) => println(s"HTTP interface running at ${binding.localAddress.getHostName}:${binding.localAddress.getPort}")
        case Failure(e) => {
          println(s"Failed to expose REST interface")
          throw e
        }
      }
    }
  }
}