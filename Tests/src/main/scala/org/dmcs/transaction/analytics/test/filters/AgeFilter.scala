package org.dmcs.transaction.analytics.test.filters

import org.dmcs.transaction.analytics.lambda.events.UserEvent

/**
  * Created by Zielony on 2016-08-06.
  */
class AgeFilter(minAge: Option[Int], maxAge: Option[Int]) extends Filter[UserEvent] {
  override def filter(event: UserEvent): Boolean = {
    (minAge, maxAge) match {
      case (Some(min), Some(max)) => (event.personalData.age.get >= min && event.personalData.age.get <= max)
      case (Some(min), None) => (event.personalData.age.get >= min)
      case (None, Some(max)) => (event.personalData.age.get <= max)
      case _ => true
    }
  }
}
