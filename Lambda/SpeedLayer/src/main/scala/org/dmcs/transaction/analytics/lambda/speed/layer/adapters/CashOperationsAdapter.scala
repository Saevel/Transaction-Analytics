package org.dmcs.transaction.analytics.lambda.speed.layer.adapters

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.CashOperation
import org.dmcs.transaction.analytics.lambda.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-02.
  */
trait CashOperationsAdapter extends Spark {

  val cashOperationsPath: String

  //TODO: merge Batch View with Real Time Views
  def withCashOperations[T](f: (Dataset[CashOperation] => T))(implicit sqlContext: SQLContext): T = {
    import sqlContext.implicits._
    f(sqlContext.read.parquet(cashOperationsPath).as[CashOperation])
  }
}
