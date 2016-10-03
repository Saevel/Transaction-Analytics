package org.dmcs.transaction.analytics.lambda.events

import java.sql.Timestamp
import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class TransactionEvent(override val timestamp: Timestamp,
                            sourceAccount: Long,
                            targetAccount: Option[Long],
                            amount: Double,
                            kind: TransactionEventType) extends Event(timestamp)
