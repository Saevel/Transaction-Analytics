package org.dmcs.transaction.analytics.tests

import org.dmcs.transaction.analytics.tests.generators.GeneratorConfiguration
import org.dmcs.transaction.analytics.tests.http.HttpConfiguration
import org.dmcs.transaction.analytics.tests.ingestors.IngestorsConfiguration
import prv.zielony.proper.model.Bundle

case class AcceptanceTestConfiguration(tests: TestConfiguration,
                                       http: HttpConfiguration,
                                       generators: GeneratorConfiguration,
                                       ingestion: IngestorsConfiguration)

object TestKind extends Enumeration {
  val Classical, Lambda = Value
}

object AcceptanceTestConfiguration {

  def apply(testKind: TestKind.Value, httpHost: String, httpPort: Int)
           (implicit bundle: Bundle): AcceptanceTestConfiguration = new AcceptanceTestConfiguration(
    TestConfiguration(testKind),
    HttpConfiguration(httpHost, httpPort),
    GeneratorConfiguration(),
    IngestorsConfiguration()
  )
}
