package org.dmcs.transaction.analyst.tests.framework.generators

import org.scalacheck.Gen

import scala.concurrent.TimeoutException
import scala.concurrent.duration.{Duration, FiniteDuration};

/**
  * Created by Zielony on 2016-10-25.
  */
trait GeneratorFactory {

  def scalacheckGenerator[T](gen: Gen[T]): Generator[T] = new Generator[T]{
    override def generate(implicit timeout: FiniteDuration): T = {
      var option = None: Option[T];
      val nanoTimeout = timeout.toNanos;
      val origin = System.nanoTime();
      do {
        if(System.nanoTime() - origin > nanoTimeout ){
          throw new TimeoutException(s"The generator did not provide a value within the timeout $timeout");
        }
        option = gen.sample
      } while(option isEmpty)
      return option.get
    }
  }

}
