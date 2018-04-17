package org.dmcs.transaction.analytics.test.framework.utils.retries

import java.time.{Duration, LocalDateTime}

import scala.concurrent.duration.FiniteDuration

trait RetryStrategy {
  def shouldRetry(history: List[FailedRun]): RetryCommand
}

object AlwaysRetry extends RetryStrategy {
  override def shouldRetry(history: List[FailedRun]) = RetryNow
}

object NeverRetry extends RetryStrategy {
  override def shouldRetry(history: List[FailedRun]) = Fail
}

class RetryUntil(timeout: FiniteDuration) extends RetryStrategy {
  override def shouldRetry(history: List[FailedRun]) = history.headOption match {
    case Some(firstRun) => if(Duration.between(firstRun.startTime, LocalDateTime.now).toMillis < timeout.toMillis) RetryNow else Fail
    case None => RetryNow
  }
}

class RetryNTimes(n: Long) extends RetryStrategy {
  override def shouldRetry(history: List[FailedRun]) = if(history.length < n) RetryNow else Fail
}

// TODO: Expotential backoff