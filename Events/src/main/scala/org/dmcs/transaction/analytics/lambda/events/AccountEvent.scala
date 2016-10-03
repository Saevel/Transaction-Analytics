package org.dmcs.transaction.analytics.lambda.events

import java.sql.Timestamp
import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class AccountEvent(override val timestamp: Timestamp,
                        userId: Long,
                        accountId: Long,
                        balance: Double,
                        kind: AccountEventType) extends Event(timestamp)