package org.dmcs.transaction.analytics.test.framework.components.generators

import java.util.concurrent.TimeoutException

import org.dmcs.transaction.analytics.test.framework.utils.retries.{Retry, RetryStrategy}
import org.scalacheck.Gen

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration

trait Generator[T] {

  def generate: Future[T]
}

object Generator {

  def apply[T](scalacheckGenerator: Gen[T], strategy: RetryStrategy)
              (implicit ex: ExecutionContext): Generator[T] =
    new Generator[T] {
      override def generate: Future[T] = Retry.async(Future(scalacheckGenerator.sample match {
        case Some(data) => data
        case None => throw NoResultException
      }))(strategy, ex)
  }
}
