package org.dmcs.transaction.analytics.tests.generators

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analytics.lambda.events._
import org.dmcs.transaction.analytics.test.framework.components.generators.Generator
import org.dmcs.transaction.analytics.test.framework.utils.retries.{NeverRetry, RetryUntil}
import org.dmcs.transaction.analytics.tests.generators.events.accounts.AccountEventsGenerator
import org.dmcs.transaction.analytics.tests.generators.entities.EntitiesModelFactory
import org.dmcs.transaction.analytics.tests.generators.events.EventsGenerator
import org.dmcs.transaction.analytics.tests.generators.events.transactions.TransactionEventsGenerator
import org.dmcs.transaction.analytics.tests.generators.events.users.{UserEventsGenerator, UserFields}
import org.dmcs.transaction.analytics.tests.model.{ApplicationModel, EntitiesModel, EventsModel}
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen

import scala.concurrent.ExecutionContext

object ApplicationModelGenerator extends EventsGenerator with EntitiesModelFactory {

  // TODO: DOX
  def apply(configuration: GeneratorConfiguration)(implicit ex: ExecutionContext): Generator[ApplicationModel] = Generator(
    eventsGenerator(configuration).map(eventsModel => ApplicationModel(eventsModel, entitiesModel(eventsModel))),
    new RetryUntil(configuration.generationTimeouts.duration)
  )
}