package org.dmcs.transaction.analytics.tests.http

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class HttpConfiguration(host: String, port: Int)

object HttpConfiguration {
  def apply()(implicit bundle: Bundle): HttpConfiguration = new HttpConfiguration(
    %("application.http.host"), %("application.http.port")
  )
}
