package org.dmcs.transaction.analyst.tests.framework

/**
  * Created by Zielony on 2016-10-12.
  */
package object matchers {

  trait Matcher {

    def stringify: String

    def matches: Boolean
  }

  case class IntMatcher(expected: Int, actual: Int) extends Matcher {
    override def stringify: String = s"Expected: $expected. Actual: $actual."

    override def matches: Boolean = (expected == actual)
  }

  case class LongMatcher(expected: Long, actual: Long) extends Matcher {
    override def stringify: String = s"Expected: $expected. Actual: $actual."

    override def matches: Boolean = (expected == actual)
  }

  case class DoubleMatcher(expected: Double, actual: Double, precision: Double) extends Matcher {

    override def stringify: String = s"Expected: $expected. Actual: $actual. Precision: $precision."

    override def matches: Boolean = (actual >= expected - precision && actual <= expected + precision)
  }

  case class FloatMatcher(expected: Float, actual: Float, precision: Float) extends Matcher {

    override def stringify: String = s"Expected: $expected. Actual: $actual. Precision: $precision."

    override def matches: Boolean = (actual >= expected - precision && actual <= expected + precision)
  }

  case class AndMatcher(first: Matcher, second: Matcher) extends Matcher {
    override def stringify: String = if(first.matches && !second.matches) {
      first.stringify
    } else if(second.matches && !first.matches) {
      second.stringify
    } else {
      s"${first.stringify}\n${second.stringify}"
    }

    override def matches: Boolean = first.matches && second.matches
  }

  case class OrMatcher(first: Matcher, second: Matcher) extends Matcher {

    override def stringify: String = s"Nither: ${first.stringify} nor: ${second.stringify}"

    override def matches: Boolean = first.matches || second.matches
  }
}
