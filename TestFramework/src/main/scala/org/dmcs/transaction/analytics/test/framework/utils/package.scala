package org.dmcs.transaction.analytics.test.framework

import scala.concurrent.{ExecutionContext, Future}

package object utils {

  implicit class FutureSequence[T](sequence: Seq[Future[T]]){

    def foldSequentially(implicit ex: ExecutionContext): Future[Seq[T]] =
      sequence.foldLeft[Future[Seq[T]]](Future.successful(Seq.empty[T]))((futureSeq, futureItem) =>
        futureSeq.flatMap(collection => futureItem.map(item => collection :+ item))
      )
  }
}
