package org.dmcs.transaction.analytics.lambda.speed.layer.rest

import akka.util.Timeout

trait DefaultTimeout {

  implicit val defaultTimeout: Timeout

}
