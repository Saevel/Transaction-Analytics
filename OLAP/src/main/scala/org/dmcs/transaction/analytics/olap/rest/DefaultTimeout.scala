package org.dmcs.transaction.analytics.olap.rest

import akka.util.Timeout

trait DefaultTimeout {

  implicit val defaultTimeout: Timeout

}
