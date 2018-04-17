package org.dmcs.transaction.analytics.test.framework.utils.retries

import scala.concurrent.duration.Duration

abstract class RetryCommand(val shouldRetry: Boolean)

case object RetryNow extends RetryCommand(true)

case class RetryLater(timeout: Duration) extends RetryCommand(true)

case object Fail extends RetryCommand(false)
