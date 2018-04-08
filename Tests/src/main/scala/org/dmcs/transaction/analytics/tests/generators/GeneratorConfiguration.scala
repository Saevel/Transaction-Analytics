package org.dmcs.transaction.analytics.tests.generators

import org.dmcs.transaction.analytics.test.framework.components.generators.GenerationTimeout
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.long

case class GeneratorConfiguration(users: UsersGeneratorConfiguration,
                                  accounts: AccountsGeneratorConfiguration,
                                  transactions: TransactionsGeneratorConfiguration,
                                  generationTimeouts: GenerationTimeout)

object GeneratorConfiguration {

  import scala.concurrent.duration._

  def apply()(implicit bundle: Bundle): GeneratorConfiguration = GeneratorConfiguration(
    UsersGeneratorConfiguration(),
    AccountsGeneratorConfiguration(),
    TransactionsGeneratorConfiguration(),
    new GenerationTimeout(long(%("generators.timeout.ms")) millis)
  )
}
