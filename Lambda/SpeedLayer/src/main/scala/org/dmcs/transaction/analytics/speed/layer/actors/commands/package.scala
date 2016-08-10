package org.dmcs.transaction.analytics.speed.layer.actors

import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-03.
  */
package object commands {

  //TODO: Docs

  /**
    * Created by Zielony on 2016-08-03.
    */
  case class AverageCapitalChangeInPeriod(from: Option[LocalDateTime], to: Option[LocalDateTime])

  /**
    * Created by Zielony on 2016-08-03.
    */
  case class AverageWithdrawalInPeriod(from: Option[LocalDateTime], to: Option[LocalDateTime])

  /**
    * Created by Zielony on 2016-08-03.
    */
  case class AverageInsertionInPeriod(from: Option[LocalDateTime], to: Option[LocalDateTime])

  case class CountAccountsByCountry(country:String)

  case class CountAccountsByCountryAndAgeInterval(country: String, minAge: Option[Int], maxAge: Option[Int])

  case object UserAgeAverage;

  case object UserAgeMedian;
}
