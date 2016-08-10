package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Actor
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{AverageCapitalChangeInPeriod, AverageInsertionInPeriod, AverageWithdrawalInPeriod}
import org.dmcs.transaction.analytics.speed.layer.adapters.CashOperationsAdapter
import org.dmcs.transaction.analytics.speed.layer.queries.CapitalQueries

/**
  * Created by Zielony on 2016-08-02.
  */
class CapitalActor extends Actor with CashOperationsAdapter with CapitalQueries {

  override def receive: Receive = {

    case AverageCapitalChangeInPeriod(start, end) => withCashOperations { operations =>
      sender ! averageCapitalChangeInPeriod(start, end)(operations)
    }
    case AverageWithdrawalInPeriod(start, end) => withCashOperations { operations =>
      sender ! averageWithdrawalInPeriod(start, end)(operations)
    }
    case AverageInsertionInPeriod(start, end) => withCashOperations { operations =>
      sender ! averageInsertionInPeriod(start, end)(operations)
    }
  }
}