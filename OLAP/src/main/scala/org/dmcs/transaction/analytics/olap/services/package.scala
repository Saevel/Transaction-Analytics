package org.dmcs.transaction.analytics.olap

import java.sql.Timestamp
import java.time.LocalDateTime

/**
  * Created by Zielony on 2017-01-23.
  */
package object services {


  private[services] implicit class DateTimeTransformer(dt: LocalDateTime) {
    def toTimestamp: Timestamp = Timestamp.valueOf(dt)
  }

  private[services] implicit class DateTimeOptionTransformer(dt: Option[LocalDateTime]) {
    def toTimestamp: Option[Timestamp] = dt.map(_.toTimestamp)
  }
}
