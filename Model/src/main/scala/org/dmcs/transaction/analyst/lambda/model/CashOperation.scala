package org.dmcs.transaction.analyst.lambda.model

import java.time.LocalDateTime

/**
  * Created by Zielony on 2016-08-02.
  */
case class CashOperation(timestamp: LocalDateTime,
                         sourceAccountId: Long,
                         targetAccountId: Option[Long],
                         kind: CashOperationType.Value,
                         amount: Double)
