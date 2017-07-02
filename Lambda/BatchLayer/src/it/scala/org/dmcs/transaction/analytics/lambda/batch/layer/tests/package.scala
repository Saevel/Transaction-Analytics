package org.dmcs.transaction.analytics.lambda.batch.layer

import java.io.File

import org.apache.spark.sql.{Dataset, SQLContext}

package object tests {

  def withData[T, R](serialize: ((Dataset[T], String) => Unit))
                 (data: Dataset[T], path: String)
                 (f: () => R)
                 (implicit sqlContext: SQLContext): R =
    try {
      clearDirectory(path)
      serialize(data, path)
      f()
    } finally {
      clearDirectory(path)
    }

  def withParquetData[T, R](data: Dataset[T], path: String)(f: () => R)(implicit sqlContext: SQLContext): R =
    withData[T, R]({ (dataset, file) =>
      dataset.toDF().write.parquet(file)
    })(data, path)(f)


  def withJsonData[T, R](data: Dataset[T], path: String)(f: () => R)(implicit sqlContext: SQLContext): R =
    withData[T, R]({ (dataset, url) =>
      dataset.toDF().write.json(url)
    })(data, path)(f)

  private[tests] def clearDirectory(path: String): Unit = {
    val directory = new File(path)
    if(directory.exists && directory.isDirectory){
      directory.listFiles.foreach(deleteFile)
      directory.delete()
    }
  }

  private[tests] def deleteFile(file: File): Boolean = file.delete()
}