package org.dmcs.transaction.analytics.test.processors

/**
  * Created by Zielony on 2016-08-05.
  */
class AveragingProcessor[InputType : Numeric] extends Processor[InputType, Double] {

  val numericOperators = implicitly[Numeric[InputType]]

  override def process(input: Traversable[InputType]): Double = {

    val (sum, count) = input
      .map((_, 1))
      .reduce { (first, second) =>
        val (firstSum, firstCount) = first
        val (secondSum, secondCount) = second
        (numericOperators.plus(firstSum, secondSum), firstCount + secondCount)
      }

    numericOperators.toDouble(sum) / count
  }
}
