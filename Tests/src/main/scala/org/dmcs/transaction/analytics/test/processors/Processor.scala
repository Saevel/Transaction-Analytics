package org.dmcs.transaction.analytics.test.processors

/**
  * Created by Zielony on 2016-08-04.
  */
trait Processor[InputType, ResultType] {

  def process(input: Traversable[InputType]): ResultType
}
