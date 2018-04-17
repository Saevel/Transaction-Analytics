package org.dmcs.transaction.analytics.tests.generators.entities

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analytics.lambda.events.{TransactionEvent, TransactionEventType}

trait CashOperationsFactory {

  protected def cashOperations(transactionEvents: Traversable[TransactionEvent]): Traversable[CashOperation] =
    transactionEvents.map(event => CashOperation(event.timestamp, event.sourceAccount, event.targetAccount, event.kind match {
      case TransactionEventType.Insertion => CashOperationType.Insertion
      case TransactionEventType.Withdrawal => CashOperationType.Withdrawal
      case TransactionEventType.Transfer => CashOperationType.Transfer
    }, event.amount))
}
