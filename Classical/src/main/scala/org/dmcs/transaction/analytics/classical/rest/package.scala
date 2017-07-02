package org.dmcs.transaction.analytics.classical

import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

package object rest {

  //TODO: Define formtter
  private val defaultDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

  private[rest] def parseTimestamp(input:String): LocalDateTime =
    LocalDateTime.parse(input, defaultDateTimeFormatter)

}
