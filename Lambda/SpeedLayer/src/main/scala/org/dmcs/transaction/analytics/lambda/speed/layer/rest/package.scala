package org.dmcs.transaction.analytics.lambda.speed.layer

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

package object rest {
  /**
    * Default date + time format.
    */
  private val defaultDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

  private[rest] def parseTimestamp(input:String): LocalDateTime =
    LocalDateTime.parse(input, defaultDateTimeFormatter)
}
