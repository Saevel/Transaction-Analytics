package org.dmcs.transaction.analytics.tests.framework.ingestors.hdfs

import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, Path}
import org.dmcs.transaction.analytics.tests.framework.data.DataFormat
import org.dmcs.transaction.analytics.tests.framework.ingestors.{Ingestor, SinkType}

import scala.concurrent.{ExecutionContext, Future}

trait HdfsIngestor[X, Y <: DataFormat[X]] extends Ingestor[SinkType.Hdfs.type, X, Y]{

  protected val fileSystem: FileSystem

  protected val path: Path

  protected def writeItem(stream: FSDataOutputStream, item: Y)(implicit context: ExecutionContext): Future[_]

  override protected def ingest(data: Traversable[Y])(implicit context: ExecutionContext): Future[_] = {

    val futureStream = Future(fileSystem.create(path))

    val dataIngested = futureStream.flatMap(stream => Future.sequence(data.map(writeItem(stream, _))))

    // val dataIngested = futureStream.flatMap(stream => Future.sequence(data.map(writeItem(stream, _)))

    dataIngested.flatMap(_ => futureStream.map(_.close))
  }

  override protected def cleanup(implicit context: ExecutionContext): Future[_] = Future (fileSystem.delete(path, true))
}
