package org.dmcs.transaction.analytics.tests.framework.ingestors.hdfs

import org.apache.hadoop.fs.FSDataOutputStream
import org.dmcs.transaction.analytics.tests.framework.data.AvroDataFormat

import scala.concurrent.{ExecutionContext, Future}

trait HdfsAvroIngestor extends HdfsIngestor[Array[Byte], AvroDataFormat]{

  override protected def writeItem(stream: FSDataOutputStream, item: AvroDataFormat)(implicit context: ExecutionContext): Future[_] =
    Future(stream.write(item.data))
}
