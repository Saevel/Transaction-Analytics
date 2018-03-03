package org.dmcs.transaction.analytics.tests.framework.generators

import org.scalacheck.Gen

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Try

/**
  * Created by kamil on 2017-09-11.
  */
trait Generator[T] {

  def generate(implicit timeout: FiniteDuration): Try[T]
}

object Generator {

  def apply[T](gen: Gen[T]): Generator[T] = new Generator[T] {

    // TODO:: Write actual retries
    override def generate(implicit timeout: FiniteDuration): Try[T] = Try {
      gen.sample match {
        case Some(result) => result
        case None => throw new IllegalStateException(s"Failed to generate non-empty input in designated time: $timeout")
      }
    }
  }
}
