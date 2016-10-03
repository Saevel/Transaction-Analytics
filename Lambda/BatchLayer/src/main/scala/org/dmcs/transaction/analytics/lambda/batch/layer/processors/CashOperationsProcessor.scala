package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.TransactionEventsAdapter
import org.dmcs.transaction.analytics.lambda.events.TransactionEventType

trait CashOperationsProcessor extends TransactionEventsAdapter {

  def constructCashOperations(implicit sqlContext: SQLContext): Dataset[CashOperation] = {
    withTransactionEvents { events =>
      import sqlContext.implicits._
      events.map(event =>
        CashOperation(event.timestamp, event.sourceAccount, event.targetAccount, mapType(event.kind), event.amount)
      )
    }
  }

  private def mapType(input: TransactionEventType): CashOperationType = input match {
    case TransactionEventType.Withdrawal => CashOperationType.Withdrawal
    case TransactionEventType.Insertion => CashOperationType.Insertion
    case TransactionEventType.Transfer => CashOperationType.Transfer
  }
}