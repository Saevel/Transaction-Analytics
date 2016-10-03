package org.dmcs.transaction.analytics.lambda.events

import java.sql.Timestamp
import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class UserEvent(id: Long,
                     override val timestamp: Timestamp,
                     username: String,
                     password: String,
                     personalData: PersonalData,
                     contactData: ContactData,
                     kind: UserEventType) extends Event(timestamp)
