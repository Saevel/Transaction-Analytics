package org.dmcs.transaction.analyst.tests.app

import org.dmcs.transaction.analyst.tests.framework.generators.GeneratorFactory
import org.dmcs.transaction.analyst.tests.framework.ingestors.Ingestor
import org.scalacheck.Arbitrary._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

/**
  * Created by Zielony on 2016-10-12.
  */
object TestsApplication extends App with GeneratorFactory {

  implicit val timeout: FiniteDuration = 5 minutes

  /*
  val mockIngestor = Ingestor[Int]{(data) => {}}

  val mockIntGenerator = scalacheckGenerator(arbitrary[Int])
  */

  MainTestSuite.run
}
