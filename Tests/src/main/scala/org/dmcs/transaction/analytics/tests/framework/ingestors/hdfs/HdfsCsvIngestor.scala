package org.dmcs.transaction.analytics.tests.framework.ingestors.hdfs

import org.apache.hadoop.fs.FSDataOutputStream
import org.dmcs.transaction.analytics.tests.framework.data.CsvDataFormat

import scala.concurrent.{ExecutionContext, Future}

trait HdfsCsvIngestor extends HdfsIngestor[String, CsvDataFormat]{

  override protected def writeItem(stream: FSDataOutputStream, item: CsvDataFormat)(implicit context: ExecutionContext): Future[_] =
    Future(stream.writeUTF(item.data))
}
