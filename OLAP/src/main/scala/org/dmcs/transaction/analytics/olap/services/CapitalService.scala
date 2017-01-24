package org.dmcs.transaction.analytics.olap.services

import java.time.LocalDateTime

import cats.data.Reader
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analytics.olap.dao.CashOperationsDao

import scala.concurrent.{ExecutionContext, Future}

trait CapitalService {

  private[olap] def averageCapitalVariance(start: Option[LocalDateTime],
                                           end: Option[LocalDateTime])
                                          (implicit executionContext: ExecutionContext): Reader[CashOperationsDao, Future[Double]] =

    findByInterval(start, end)
      .andThen(future => future.map(operations => operations.map{
        case CashOperation(_, _, _, CashOperationType.Withdrawal, amount) => (-1) * amount
        case CashOperation(_, _, _, CashOperationType.Transfer, _) => 0.0
        case CashOperation(_, _, _, CashOperationType.Insertion, amount) => amount
      } reduce((first, second) => (first + second) / operations.length)))

  private[olap] def averageOperationValue(kind: CashOperationType,
                                          start: Option[LocalDateTime],
                                          end: Option[LocalDateTime])
                                         (implicit executionContext: ExecutionContext): Reader[CashOperationsDao, Future[Double]] =
    findByKindAndInterval(kind, start, end)
      .andThen(future => future.map(operations =>
        operations.map(_.amount).reduce((first, second) => (first + second) / operations.length))
      )

  private[services] def findByKindAndInterval(kind: CashOperationType, start: Option[LocalDateTime],
                                       end: Option[LocalDateTime]): Reader[CashOperationsDao, Future[List[CashOperation]]] =
    Reader(dao => (start, end) match {
      case (Some(s), Some(e)) => dao.findByTypeAndTimeInterval(kind, s.toTimestamp, e.toTimestamp)
      case (Some(s), None) => dao.findByTypeAndStartTimestamp(kind, s.toTimestamp)
      case (None, Some(e)) => dao.findByTypeAndEndTimestamp(kind, e.toTimestamp)
      case _ => dao.findByType(kind)
    })

  private[services] def findByInterval(start: Option[LocalDateTime],
                                              end: Option[LocalDateTime]): Reader[CashOperationsDao, Future[List[CashOperation]]] =
    Reader(dao => (start, end) match {
      case (Some(s), Some(e)) => dao.findByTimeInterval(s.toTimestamp, e.toTimestamp)
      case (Some(s), None) => dao.findByStartTimestamp(s.toTimestamp)
      case (None, Some(e)) => dao.findByEndTimestamp(e.toTimestamp)
      case _ => dao.findAll
    })
}
