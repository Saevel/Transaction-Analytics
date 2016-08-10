package org.dmcs.transaction.analytics.test.processors

/**
  * Created by Zielony on 2016-08-05.
  */
class CountingProcessor[InputType] extends Processor[InputType, Int] {

  override def process(input: Traversable[InputType]): Int = input.size
}
