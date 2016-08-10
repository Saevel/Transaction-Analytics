package org.dmcs.transaction.analytics.lambda.events

import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class AccountEvent(override val timestamp: LocalDateTime,
                        userId: Long,
                        accountId: Long,
                        balance: Double,
                        kind: AccountEventType.Value) extends Event(timestamp)