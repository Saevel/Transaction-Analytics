package org.dmcs.transaction.analytics.speed.layer.rest

import akka.util.Timeout

/**
  * Created by Zielony on 2016-10-01.
  */
trait DefaultTimeout {

  implicit val defaultTimeout: Timeout

}
