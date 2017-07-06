package org.dmcs.transaction.analytics.classical
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.Timeout
import org.dmcs.transaction.analytics.classical.dao.DataAccessLayer
import org.dmcs.transaction.analytics.classical.rest.{DefaultTimeout, RestInterface}
import prv.zielony.proper.inject._
import prv.zielony.proper.converters._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


object ClassicalAnalyticsApplication extends App with RestInterface with DefaultTimeout {

  implicit val bundle: Bundle = load(classpath :/ "application.properties")

  implicit val system: ActorSystem = ActorSystem("ClassicalAnalyticsApplication")

  implicit val materializer: Materializer = ActorMaterializer()

  override implicit val defaultTimeout: Timeout = Duration(%("http.default.timeout.seconds"), "seconds")

  DataAccessLayer.create(Duration(%("cassandra.autocreate.timeout"), "seconds"))

  exposeRestInterface(%("http.default.host"), %("http.default.port"))
}