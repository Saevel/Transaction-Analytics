package org.dmcs.transaction.analytics.test.filters

import java.time.LocalDateTime

import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, Event}

/**
  * Created by Zielony on 2016-08-06.
  */
class PeriodFilter[T <: Event](startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) extends Filter[T]{

  override def filter(event: T): Boolean = (startDate, endDate) match {
    case (Some(start), Some(end)) => (event.timestamp.isAfter(start) && event.timestamp.isBefore(end))
    case (Some(start), None) => (event.timestamp.isAfter(start))
    case (None, Some(end)) => (event.timestamp.isBefore(end))
    case _ => true
  }
}
