package org.dmcs.transaction.analytics.tests.framework.model

/**
  * Created by kamil on 2017-09-13.
  */
trait BasicEquallities {

  object BasicEquallity {

    def apply[T]: Equallity[T] = new Equallity[T] {
      override def areEqual(first: T, second: T): Boolean = first == second
    }
  }

  implicit val basicAnyEquallity: Equallity[Any] = BasicEquallity[Any]

  implicit val basicStringEquallity: Equallity[String] = BasicEquallity[String]

  implicit val basicIntEquallity: Equallity[Int] = BasicEquallity[Int]

  // TODO: Version with default precision
  implicit val basicDoubleEquallity: Equallity[Double] = new Equallity[Double]{
    override def areEqual(first: Double, second: Double): Boolean = (Math.abs(first - second) <= Double.MinPositiveValue)
  }

  implicit val basicLongEquallity: Equallity[Long] = BasicEquallity[Long]

  // TODO: Version with default precision
  implicit val basicFloatEquallity: Equallity[Float] = new Equallity[Float]{
    override def areEqual(first: Float, second: Float): Boolean = (Math.abs(first - second) <= Float.MinPositiveValue)
  }

  implicit val basicByteEquallity: Equallity[Byte] = BasicEquallity[Byte]

  implicit val basicShortEquallity: Equallity[Short] = BasicEquallity[Short]
}
