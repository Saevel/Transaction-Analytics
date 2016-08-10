package org.dmcs.transaction.analytics.speed.layer.adapters

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.CashOperation
import org.dmcs.transaction.analytics.speed.layer.spark.Spark

/**
  * Created by Zielony on 2016-08-02.
  */
trait CashOperationsAdapter extends Spark {

  //TODO: Read real data
  //TODO: merge Batch View with Real Time Views
  def withCashOperations[T](f: (Dataset[CashOperation] => T)): T = withSparkSql { sqlContext =>
    f(sqlContext.read.json("").as[CashOperation])
  }
}
