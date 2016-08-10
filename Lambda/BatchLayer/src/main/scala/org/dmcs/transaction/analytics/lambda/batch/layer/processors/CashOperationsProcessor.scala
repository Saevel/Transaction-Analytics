package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.CashOperation
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.TransactionEventsAdapter

/**
  * Created by Zielony on 2016-08-04.
  */
trait CashOperationsProcessor extends TransactionEventsAdapter {

  def constructCashOperations: Dataset[CashOperation] = {
    withTransactionEvents { events =>
      //TODO: Map to Model
      ???
    }
  }
}
