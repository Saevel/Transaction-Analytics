package org.dmcs.transaction.analytics.test.actors

import akka.actor.Actor

import org.dmcs.transaction.analytics.test.filters.Filter
import org.dmcs.transaction.analytics.test.processors.Processor

/**
  * Created by Zielony on 2016-08-07.
  */
class PredictionActor[DataType, PredictionType](filters: Seq[Filter[DataType]],
                                                processor: Processor[DataType, PredictionType]) extends Actor {
  override def receive: Receive = {

    case data: Traversable[DataType] => {
     sender ! processor.process(filters flatMap { f =>
       data.filter(f.filter(_))
     })
    }
  }

  //TODO: Ask a separate actor for cached events by type
}
