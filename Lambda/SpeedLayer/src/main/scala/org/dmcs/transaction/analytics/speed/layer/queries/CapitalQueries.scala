package org.dmcs.transaction.analytics.speed.layer.queries

import java.sql.Timestamp
import java.time.LocalDateTime

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analyst.lambda.model.CashOperationType._
import org.dmcs.transaction.analytics.speed.layer._

/**
  * Created by Zielony on 2016-08-02.
  */
trait CapitalQueries {

  def averageWithdrawalInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) = withKind(Withdrawal)(
    inPeriod(startDate, endDate) { operations =>
      import operations.sqlContext.implicits._
      operations.map(_.amount).average
    }
  )

  def averageInsertionInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) = withKind(Insertion)(
    inPeriod(startDate, endDate) { operations =>
      import operations.sqlContext.implicits._
      operations.map(_.amount).average
    }
  )

  def averageCapitalChangeInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) =
    inPeriod(startDate, endDate) { operations =>
      import operations.sqlContext.implicits._
      operations
        .map( operation =>
          if(operation.kind == Withdrawal) (-1) * operation.amount
          else if(operation.kind == Insertion) operation.amount
          else 0.0
        )
        .reduce(_ + _)
    }

  private[queries] def withKind[T](kind: CashOperationType)(f: (Dataset[CashOperation] => T))
                                :(Dataset[CashOperation] => T) = { operations =>
    import operations.sqlContext.implicits._
    f(operations.filter(_.kind == kind))
  }

  private[queries] def inPeriod[T](startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                                  (f: Dataset[CashOperation] => T): (Dataset[CashOperation] => T) = { operations =>
    import operations.sqlContext.implicits._
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