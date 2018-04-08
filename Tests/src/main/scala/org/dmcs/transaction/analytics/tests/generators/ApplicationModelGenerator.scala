package org.dmcs.transaction.analytics.tests.generators

import org.dmcs.transaction.analytics.test.framework.components.generators.Generator
import org.dmcs.transaction.analytics.tests.AcceptanceTestConfiguration
import org.dmcs.transaction.analytics.tests.model.ApplicationModel
import org.scalacheck.Gen

object ApplicationModelGenerator {

  def apply(configuration: GeneratorConfiguration): Generator[ApplicationModel] = ??? // Gen.listOfN(configuration.Gen.uuid)
}
