package org.dmcs.transaction.analytics.lambda.speed.layer.actors

import akka.actor.Actor
import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.lambda.speed.layer.actors.commands.{CapitalChangeInPeriod, AverageInsertionInPeriod, AverageWithdrawalInPeriod}
import org.dmcs.transaction.analytics.lambda.speed.layer.adapters.CashOperationsAdapter
import org.dmcs.transaction.analytics.lambda.speed.layer.queries.CapitalQueries

/**
  * Created by Zielony on 2016-08-02.
  */
class CapitalActor(private val cashOperationsLocation: String, private val sqlContext: SQLContext)
  extends Actor with CashOperationsAdapter with CapitalQueries {

  override val cashOperationsPath: String = cashOperationsLocation

  implicit val sql: SQLContext = sqlContext

  override def receive: Receive = {

    case CapitalChangeInPeriod(start, end) => withCashOperations { operations =>
      sender ! capitalChangeInPeriod(start, end)(sql)(operations)
    }
    case AverageWithdrawalInPeriod(start, end) => withCashOperations { operations =>
      sender ! averageWithdrawalInPeriod(start, end)(sql)(operations)
    }
    case AverageInsertionInPeriod(start, end) => withCashOperations { operations =>
      sender ! averageInsertionInPeriod(start, end)(sql)(operations)
    }
  }
}