package org.dmcs.transaction.analytics.olap


import akka.util.Timeout

import org.dmcs.transaction.analytics.olap.rest.{DefaultTimeout, RestInterface}
import org.dmcs.transaction.analytics.olap.dao._

import prv.zielony.proper.inject._
import prv.zielony.proper.converters._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.duration
import scala.concurrent.duration._

object Application extends App with RestInterface with DefaultTimeout {

  implicit val bundle: Bundle = load(classpath :/ "application.properties")

  override implicit val defaultTimeout: Timeout = Duration(%("http.default.timeout.seconds"), "seconds")

  DataAccessLayer.create(Duration(%("cassandra.autocreate.timeout"), "seconds"))

  exposeRestInterface(%("http.default.host"), %("http.default.port"))
}