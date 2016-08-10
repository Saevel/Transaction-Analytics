package org.dmcs.transaction.analytics.lambda.events

import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class TransactionEvent(override val timestamp: LocalDateTime,
                            sourceAccount: Long,
                            targetAccount: Option[Long],
                            amount: Double,
                            kind: TransactionEventType.Value) extends Event(timestamp)
