package org.dmcs.transaction.analyst.tests.app.generators

import org.dmcs.transaction.analyst.tests.app.model.{EntitiesModel, EventsModel, TestModel}
import org.dmcs.transaction.analyst.tests.framework.generators.{Generator, GeneratorFactory}
import org.scalacheck.Gen

import scala.concurrent.duration.FiniteDuration

/**
  * Created by Zielony on 2016-10-28.
  */
trait TestModelGenerator extends Generator[TestModel] with GeneratorFactory with EntitiesGenerator with EventsGenerator {

  val config: TestModelConfig;

  def modelGenerator: Gen[TestModel] = for {
    entitiesModel <- entitiesGenerator(config.userCount, config.accountsPerPerson, config.transactionsPerAccount,
      config.transactionSpread, config.minimalInitialAmount)
    eventsModel <- eventsModelGenerator(entitiesModel, config.countries: _*)
  } yield TestModel(eventsModel, entitiesModel)

  protected val generator = scalacheckGenerator(modelGenerator)

  override def generate(implicit timeout: FiniteDuration): TestModel = generator.generate
}

object TestModelGenerator {

  def apply(modelConfig: TestModelConfig): TestModelGenerator = new TestModelGenerator {
    override val config: TestModelConfig = modelConfig
  }
}

case class TestModelConfig(userCount: Long, accountsPerPerson: Double, transactionsPerAccount: Double,
                           transactionSpread: Double, minimalInitialAmount: Double, countries: Seq[String])
