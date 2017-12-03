package org.dmcs.transaction.analytics.classical.dao

import java.sql.Timestamp

import com.outworkers.phantom.dsl._
import org.dmcs.transaction.analyst.lambda.model.{CashOperation, CashOperationType}
import org.dmcs.transaction.analytics.lambda.events.{TransactionEvent, TransactionEventType}

import scala.concurrent.Future

class CashOperations extends CassandraTable[CashOperationsDao, CashOperation]{

  object timestamp extends DateTimeColumn(this) with PartitionKey

  object sourceAccountId extends LongColumn(this)

  object targetAccountId extends OptionalLongColumn(this)

  object amount extends DoubleColumn(this)

  object kind extends StringColumn(this) with Index

  override def fromRow(row: Row): CashOperation =
    CashOperation(timestamp(row).toTimestamp, sourceAccountId(row), targetAccountId(row),
      CashOperationType.fromString(kind(row)) ,amount(row))
}

abstract class CashOperationsDao extends CashOperations with RootConnector {

  def findAll: Future[List[CashOperation]] = select.fetch

  def findByType(kind: CashOperationType): Future[List[CashOperation]] =
    select.where(_.kind eqs kind.toString).fetch

  def findByTypeAndStartTimestamp(kind: CashOperationType, startTimestamp: Timestamp): Future[List[CashOperation]] =
    select.where(_.kind eqs kind.toString).and(_.timestamp >= startTimestamp.toDateTime).fetch

  def findByTypeAndEndTimestamp(kind: CashOperationType, endTimestamp: Timestamp): Future[List[CashOperation]] =
    select.where(_.kind eqs kind.toString).and(_.timestamp <= endTimestamp.toDateTime).fetch

  def findByTypeAndTimeInterval(kind: CashOperationType, start: Timestamp, end: Timestamp): Future[List[CashOperation]] =
    select.where(_.kind eqs kind.toString).and(_.timestamp >= start.toDateTime).and(_.timestamp <= end.toDateTime).fetch

  def findByStartTimestamp(startTimestamp: Timestamp): Future[List[CashOperation]] =
    select.where(_.timestamp >= startTimestamp.toDateTime).fetch

  def findByEndTimestamp(endTimestamp: Timestamp): Future[List[CashOperation]] =
    select.where(_.timestamp <= endTimestamp.toDateTime).fetch

  def findByTimeInterval(start: Timestamp, end: Timestamp): Future[List[CashOperation]] =
    select.where(_.timestamp >= start.toDateTime).and(_.timestamp <= end.toDateTime).fetch
}