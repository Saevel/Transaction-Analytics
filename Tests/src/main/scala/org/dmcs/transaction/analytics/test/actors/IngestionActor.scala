package org.dmcs.transaction.analytics.test.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.dmcs.transaction.analytics.test.ingestors.Ingestor

/**
  * Created by Zielony on 2016-08-07.
  */
class IngestionActor[DataType](ingestor: Ingestor[DataType]) extends Actor {

  override def receive: Receive = {

    case data: Traversable[DataType] => ingestor ingest data
      //TODO: ACK to sender!
  }
}
