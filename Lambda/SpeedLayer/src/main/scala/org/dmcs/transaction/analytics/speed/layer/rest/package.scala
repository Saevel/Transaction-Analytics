package org.dmcs.transaction.analytics.speed.layer

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
  * Created by Zielony on 2016-08-01.
  */
package object rest {

  //TODO: Define formtter
  private val defaultDateTimeFormatter: DateTimeFormatter = _

  private[rest] def parseTimestamp(input:String): LocalDateTime =
    LocalDateTime.parse(input, defaultDateTimeFormatter)
}
