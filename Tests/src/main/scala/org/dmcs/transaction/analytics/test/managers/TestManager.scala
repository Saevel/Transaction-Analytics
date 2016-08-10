package org.dmcs.transaction.analytics.test.managers

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern._
import org.dmcs.transaction.analytics.test.actors.{IngestionActor, PredictionActor}
import org.dmcs.transaction.analytics.test.dsl.TestConfig
import org.scalacheck.Gen

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Zielony on 2016-08-04.
  */
class TestManager {

  def run[DataType, ResultType](testConfig: TestConfig[DataType, ResultType])
                               (implicit conversion: (Any => ResultType), actorSystem: ActorSystem): Double = {

    val ingestionActor: ActorRef = actorSystem.actorOf(Props(new IngestionActor(testConfig.ingestor)))

    val predictionActor: ActorRef = actorSystem.actorOf(Props(
      new PredictionActor(testConfig.filters, testConfig.processor))
    )

    val data = generateData(testConfig generator)
    Await.result(
      for {
        ack <- ingestionActor ? data
        prediction <- (predictionActor ? data) map(conversion)
        result <- testConfig checkResult
      } yield testConfig.evaluator evaluate(prediction, result),
      3 seconds)
  }

  private[managers] def generateData[DataType, ResultType](generator: Gen[Traversable[DataType]]): Traversable[DataType] = {
    var sample: Option[Traversable[DataType]] = None;

    do {
      sample = generator sample
    } while(sample.isEmpty || sample.get.isEmpty)

    sample.get
  }
}