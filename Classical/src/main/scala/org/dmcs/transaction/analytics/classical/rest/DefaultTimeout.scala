package org.dmcs.transaction.analytics.classical.rest

import akka.util.Timeout

trait DefaultTimeout {

  implicit val defaultTimeout: Timeout

}
