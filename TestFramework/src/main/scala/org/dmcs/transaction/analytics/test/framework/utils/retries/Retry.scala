package org.dmcs.transaction.analytics.test.framework.utils.retries

import java.time.LocalDateTime

import scala.concurrent.{ExecutionContext, Future}

object Retry {

  def async[T](future: => Future[T])(implicit strategy: RetryStrategy, ex: ExecutionContext): Future[T] =
    retryAsyncInternal(future, List.empty, strategy, LocalDateTime.now)

  private[retries] def retryAsyncInternal[T](future: => Future[T], history: List[FailedRun], strategy: RetryStrategy, startTimestamp: LocalDateTime)
                                            (implicit ex: ExecutionContext): Future[T] = future.recoverWith{
    case cause => {
      val updatedHistory = history :+ FailedRun(startTimestamp, LocalDateTime.now, cause)
      strategy.shouldRetry(updatedHistory) match {
        case Fail => Future.failed(cause)
        case RetryLater(timeout) => Future(Thread.sleep(timeout.toMillis)).flatMap(_ => retryAsyncInternal(future, updatedHistory, strategy, LocalDateTime.now))
        case RetryNow => retryAsyncInternal(future, updatedHistory, strategy, LocalDateTime.now)
      }
    }
  }
}
