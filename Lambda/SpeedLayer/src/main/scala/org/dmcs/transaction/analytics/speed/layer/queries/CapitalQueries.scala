package org.dmcs.transaction.analytics.speed.layer.queries

import java.sql.Timestamp
import java.time.LocalDateTime

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analyst.lambda.model.CashOperationType._
import org.dmcs.transaction.analytics.speed.layer._
import org.dmcs.transaction.analytics.speed.layer.adapters.CashOperationsAdapter

trait CapitalQueries {

  def averageWithdrawalInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                               (implicit sqlContext: SQLContext): (Dataset[CashOperation] => Double) = { operations =>
    inPeriod(startDate, endDate) { operations =>
        import sqlContext.implicits._
        operations.filter(_.kind == Withdrawal).map(_.amount).average
    }(sqlContext)(operations)
  }

  def averageInsertionInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                              (implicit sqlContext: SQLContext): (Dataset[CashOperation] => Double) = { operations =>
    inPeriod(startDate, endDate) { operations =>
      import sqlContext.implicits._
      operations.filter(_.kind == Insertion).map(_.amount).average
    }(sqlContext)(operations)
  }

  def capitalChangeInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                           (implicit sqlContext: SQLContext) =
    inPeriod(startDate, endDate) { operations =>
      import sqlContext.implicits._
      operations.map(operation => operation.kind match {
          case Withdrawal => (-1) * operation.amount
          case Insertion => operation.amount
          case _ => 0.0
        }) reduce(_ + _)
    }

  private[queries] def inPeriod[T](startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                                  (f: Dataset[CashOperation] => T)
                                  (implicit sqlContext: SQLContext): (Dataset[CashOperation] => T) = { operations =>
    import sqlContext.implicits._
    f(
      (startDate, endDate) match {
        case (Some(start), Some(end)) => operations between start and end
        case (Some(start), None) => operations after start
        case (None, Some(end)) => operations before end
        case _ => operations
      }
    )
  }

  private implicit class CashOperationsBefore(operations: Dataset[CashOperation]) {
    import operations.sqlContext.implicits._
    def before(maxDate: LocalDateTime): Dataset[CashOperation] =
      operations.filter(_.timestamp.after(Timestamp.valueOf(maxDate)))
  }

  private implicit class CashOperationsBetweenLower(operations: Dataset[CashOperation]) {
    import operations.sqlContext.implicits._
    def between(minDate: LocalDateTime): CashOperationsBetweenUpper =
      new CashOperationsBetweenUpper(operations after minDate)
  }

  private class CashOperationsBetweenUpper(operations: Dataset[CashOperation]) {
    import operations.sqlContext.implicits._
    def and(maxDate: LocalDateTime): Dataset[CashOperation] = operations before maxDate
  }

  private implicit class CashOperationsAfter(operations: Dataset[CashOperation]) {
    import operations.sqlContext.implicits._
    def after(minDate: LocalDateTime): Dataset[CashOperation] =
      operations.filter(_.timestamp.after(Timestamp.valueOf(minDate)))
  }
}