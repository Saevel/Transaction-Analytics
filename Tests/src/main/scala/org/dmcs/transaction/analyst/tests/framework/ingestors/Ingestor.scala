package org.dmcs.transaction.analyst.tests.framework.ingestors

/**
  * Created by Zielony on 2016-10-12.
  */
trait Ingestor[T] {

  def ingest(data: Traversable[T]): Unit
}

object Ingestor {
  def apply[T](f: (Traversable[T] => Unit)): Ingestor[T] = new Ingestor[T] {
    override def ingest(data: Traversable[T]): Unit = f(data)
  }
}