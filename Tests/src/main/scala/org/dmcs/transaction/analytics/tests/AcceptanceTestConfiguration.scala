package org.dmcs.transaction.analytics.tests

import org.dmcs.transaction.analytics.tests.generators.GeneratorConfiguration
import org.dmcs.transaction.analytics.tests.http.HttpConfiguration
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.converters.long

import scala.concurrent.duration._

case class AcceptanceTestConfiguration(kind: TestKind.Value,
                                       outputFile: String,
                                       phaseCount: Int,
                                       testTimeout: Duration,
                                       doublePrecision: Double,
                                       http: HttpConfiguration,
                                       generators: GeneratorConfiguration)

object TestKind extends Enumeration {
  val Classical, Lambda = Value
}

object AcceptanceTestConfiguration {
  def apply()(implicit bundle: Bundle): AcceptanceTestConfiguration = new AcceptanceTestConfiguration(
    TestKind.withName(%("test.kind")),
    %("reporter.output.file"),
    %("phase.count"),
    (long(%("test.timeout.ms")) millis),
    %("double.precision"),
    HttpConfiguration(),
    GeneratorConfiguration()
  )
}
