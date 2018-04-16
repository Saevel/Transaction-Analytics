package org.dmcs.transaction.analytics.tests

import prv.zielony.proper.converters.long
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.inject._
import prv.zielony.proper.model.Bundle

import scala.concurrent.duration._

case class TestConfiguration(kind: TestKind.Value,
                             outputFile: String,
                             phaseCount: Int,
                             testTimeout: Duration,
                             doublePrecision: Double)

object TestConfiguration {

  def apply(testKind: TestKind.Value)(implicit bundle: Bundle): TestConfiguration = new TestConfiguration(
    testKind,
    %("reporter.output.file"),
    %("test.phases"),
    (long(%("test.timeout.ms")) millis),
    %("test.double.precision")
  )
}
