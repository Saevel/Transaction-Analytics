package org.dmcs.transaction.analytics.speed.layer

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import org.dmcs.transaction.analytics.speed.layer.actors.{AccountsActor, CapitalActor, ClientsActor}
import org.dmcs.transaction.analytics.speed.layer.rest.{DefaultTimeout, RestInterface}
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

import scala.concurrent.duration._
import prv.zielony.proper.inject._
import prv.zielony.proper.converters._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

/**
  * Created by Zielony on 2016-08-01.
  */
object Main extends Spark {

  import scala.concurrent.ExecutionContext.Implicits._

  implicit val actorSystem: ActorSystem = ActorSystem("SpeedLayer")

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

      rest.exposeRestInterface("localhost", int(%("http.interface.port" @@ speedProperties)))
    }
  }
}