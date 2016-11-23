package org.dmcs.transaction.analyst.tests.app.generators

import org.dmcs.transaction.analyst.tests.app.model.{EntitiesModel, EventsModel, TestModel}
import org.dmcs.transaction.analyst.tests.framework.generators.{Generator, GeneratorFactory}
import org.scalacheck.Gen

import scala.concurrent.duration.FiniteDuration

class TestModelGenerator(config: TestModelConfig) extends Generator[TestModel] with GeneratorFactory with EntitiesGenerator with EventsGenerator {

  private def modelGenerator: Gen[TestModel] = for {
    entitiesModel <- entitiesGenerator(config.userCount, config.accountsPerPerson, config.transactionsPerAccount,
      config.transactionSpread, config.minimalInitialAmount)
    eventsModel <- eventsModelGenerator(entitiesModel, config.countries: _*)
  } yield TestModel(eventsModel, entitiesModel)

  private val scalacheckGen = scalacheckGenerator(modelGenerator)

  override def generate(implicit timeout: FiniteDuration): TestModel = scalacheckGen.generate
}

object TestModelGenerator {
  def apply(modelConfig: TestModelConfig): TestModelGenerator = new TestModelGenerator(modelConfig)
}

case class TestModelConfig(userCount: Long, accountsPerPerson: Double, transactionsPerAccount: Double,
                           transactionSpread: Double, minimalInitialAmount: Double, countries: Seq[String])