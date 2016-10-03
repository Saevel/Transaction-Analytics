package org.dmcs.transaction.analytics.test.filters

import org.dmcs.transaction.analytics.lambda.events.UserEvent

/**
  * Created by Zielony on 2016-08-06.
  */
class CountryFilter(country: String) extends Filter[UserEvent]{
  override def filter(event: UserEvent): Boolean =
    (event.contactData.country != null && event.contactData.country == country)
}
