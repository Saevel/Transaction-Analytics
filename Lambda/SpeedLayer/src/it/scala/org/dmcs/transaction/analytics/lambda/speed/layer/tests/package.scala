package org.dmcs.transaction.analytics.lambda.speed.layer

import java.io.File

import akka.actor.ActorSystem
import org.apache.spark.sql.{Dataset, SQLContext}

/**
  * Created by Zielony on 2016-10-04.
  */
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
      //\clearDirectory(path)
    }

  def withParquetData[T, R](data: Dataset[T], path: String)(f: () => R)(implicit sqlContext: SQLContext): R =
    withData[T, R]({ (dataset, file) =>
      dataset.toDF().write.parquet(file)
    })(data, path)(f)


  def withJsonData[T, R](data: Dataset[T], path: String)(f: () => R)(implicit sqlContext: SQLContext): R =
    withData[T, R]({ (dataset, url) =>
      dataset.toDF().write.json(url)
    })(data, path)(f)

  def withActorSystem[T](f: (ActorSystem => T)): T = {
    var actorSystemOption: Option[ActorSystem] = None
    try {
      actorSystemOption = Option(ActorSystem("SpeedLayerIntegrationTests"))
      f(actorSystemOption.get)
    } finally {
      actorSystemOption.foreach(_.shutdown())
    }
  }

  implicit class CollectionWithAverage[T : Numeric](collection: Traversable[T]) {

    val operations = implicitly[Numeric[T]]

    def avg: Double = {
      val sum = collection.fold(operations.zero)((x, y) => operations.plus(x, y))
      (operations.toDouble(sum) / collection.size)
    }
  }

  private[tests] def clearDirectory(path: String): Unit = {
    val directory = new File(path)
    if(directory.exists && directory.isDirectory){
      directory.listFiles.foreach(deleteFile)
      directory.delete()
    }
  }

  private[tests] def deleteFile(file: File): Boolean = file.delete()


}
