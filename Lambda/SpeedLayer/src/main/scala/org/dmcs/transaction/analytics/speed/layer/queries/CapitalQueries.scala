package org.dmcs.transaction.analytics.speed.layer.queries

import java.time.LocalDateTime

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analyst.lambda.model.CashOperationType._

/**
  * Created by Zielony on 2016-08-02.
  */
trait CapitalQueries {

  def averageWithdrawalInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) = withKind(Withdrawal)(
    inPeriod(startDate, endDate) { operations =>
      operations.map(_.amount).average
    }
  )

  def averageInsertionInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) = withKind(Insertion)(
    inPeriod(startDate, endDate) { operations =>
      operations.map(_.amount).average
    }
  )

  def averageCapitalChangeInPeriod(startDate: Option[LocalDateTime], endDate: Option[LocalDateTime]) =
    inPeriod(startDate, endDate) { operations =>
      operations
        .map( operation =>
          if(operation.kind == Withdrawal) (-1) * operation.amount
          else if(operation.kind == Insertion) operation.amount
          else 0.0
        )
        .reduce(_ + _)
    }

  private[queries] def withKind[T](kind: CashOperationType.Value)(f: (Dataset[CashOperation] => T))
                                :(Dataset[CashOperation] => T) = { operations =>
    f(operations.filter(_.kind == kind))
  }

  private[queries] def inPeriod[T](startDate: Option[LocalDateTime], endDate: Option[LocalDateTime])
                                  (f: Dataset[CashOperation] => T): (Dataset[CashOperation] => T) = { operations =>
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
    def before(maxDate: LocalDateTime): Dataset[CashOperation] =
      operations.filter(_.timestamp isBefore maxDate)
  }

  private implicit class CashOperationsBetweenLower(operations: Dataset[CashOperation]) {
    def between(minDate: LocalDateTime): CashOperationsBetweenUpper =
      new CashOperationsBetweenUpper(operations after minDate)
  }

  private class CashOperationsBetweenUpper(operations: Dataset[CashOperation]) {
    def and(maxDate: LocalDateTime): Dataset[CashOperation] = operations before maxDate
  }

  private implicit class CashOperationsAfter(operations: Dataset[CashOperation]) {
    def after(minDate: LocalDateTime): Dataset[CashOperation] = operations.filter(_.timestamp isAfter minDate)
  }
}