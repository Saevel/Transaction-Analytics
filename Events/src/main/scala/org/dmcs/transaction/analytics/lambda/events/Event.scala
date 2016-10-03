package org.dmcs.transaction.analytics.lambda.events

import java.sql.Timestamp
import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
class Event(val timestamp: Timestamp) extends Serializable
