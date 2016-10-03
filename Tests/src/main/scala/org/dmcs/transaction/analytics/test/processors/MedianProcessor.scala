package org.dmcs.transaction.analytics.test.processors

/**
  * Created by Zielony on 2016-08-05.
  */
class MedianProcessor[InputType : Numeric] extends Processor[InputType, InputType] {

  val numericOperator = implicitly[Numeric[InputType]]

  override def process(input: Traversable[InputType]): InputType = {

    val middleIndex = Math.floorDiv(input.size, 2)

    input
      .toList
      .sortWith((x, y) => numericOperator.gt(x, y))
      .apply(middleIndex)
  }
}
