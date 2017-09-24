package org.dmcs.transaction.analytics.tests.app.generators

import org.dmcs.transaction.analytics.tests.app.model.TestModel
import org.dmcs.transaction.analytics.tests.framework.generators.Generator
import org.scalacheck.Gen

import scala.concurrent.duration.FiniteDuration
import scala.util.Try

trait TestModelGenerator extends Generator[TestModel] with EntitiesGenerator with EventsGenerator {

  protected val config: TestModelConfig

  private def modelGenerator: Gen[TestModel] = for {
    entitiesModel <- entitiesGenerator(config.userCount, config.accountsPerPerson, config.transactionsPerAccount,
      config.transactionSpread, config.minimalInitialAmount)
    eventsModel <- eventsModelGenerator(entitiesModel, config.countries: _*)
  } yield TestModel(eventsModel, entitiesModel)

  private val scalacheckGenerator = Generator(modelGenerator)

  override def generate(implicit timeout: FiniteDuration): Try[TestModel] = scalacheckGenerator.generate
}

object TestModelGenerator {
  def apply(modelConfig: TestModelConfig): TestModelGenerator = new TestModelGenerator{
    override val config: TestModelConfig = modelConfig
  }
}

case class TestModelConfig(userCount: Long, accountsPerPerson: Double, transactionsPerAccount: Double,
                           transactionSpread: Double, minimalInitialAmount: Double, countries: Seq[String])