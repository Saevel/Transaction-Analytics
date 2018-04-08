package org.dmcs.transaction.analytics.test.framework.components.generators

import scala.concurrent.duration.Duration

class GenerationTimeoutException(timeout: Duration, message: Option[String] = None)
  extends Exception(s"Timeout exceeded. Value: ${timeout}. " + message.fold("")(msg => s"Message: $msg."))
