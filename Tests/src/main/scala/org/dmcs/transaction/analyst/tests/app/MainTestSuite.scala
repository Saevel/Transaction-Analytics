package org.dmcs.transaction.analyst.tests.app

import java.io.File

import org.dmcs.transaction.analyst.tests.app.generators.{TestModelConfig, TestModelGenerator}
import org.dmcs.transaction.analyst.tests.app.json.TestModelJsonWrites
import org.dmcs.transaction.analyst.tests.app.model.TestModel
import org.dmcs.transaction.analyst.tests.framework.ingestors.JsonLinesIngestor
import org.dmcs.transaction.analyst.tests.framework.loggers.ConsoleLogger
import org.dmcs.transaction.analyst.tests.framework.model.TestSuite
import org.dmcs.transaction.analyst.tests.framework.reporters.CsvReporter
import play.api.libs.json.Writes
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

import scala.concurrent.duration.FiniteDuration

object MainTestSuite extends TestSuite[TestModel] with ConsoleLogger with JsonLinesIngestor[TestModel] with CsvReporter
  with TestModelGenerator {

  module("Transaction Analytics") { implicit m =>

    should("calculate user age median"){ list =>
      1 === 1
    }

    should("calculate user age average") { list =>
      1 === 1
    }

    should("calculate overall capital variance") { list =>
      1 === 1
    }
  }

  implicit val applicationProperties = load(classpath :/ "application.properties")

  override val ingestionTargetFile: File = new File(%("ingestion.target.file"))

  override val separator: Char = ','

  override val reportOutput: File = new File(%("reporting.output.file"))

  override implicit val writes: Writes[TestModel] = TestModelJsonWrites.modelWrites

  override val config: TestModelConfig = TestModelConfig(
    userCount = %("users.count"),
    accountsPerPerson = %("accounts.per.person"),
    transactionsPerAccount = %("transactions.per.account"),
    transactionSpread = %("transactions.spread"),
    minimalInitialAmount = %("minimal.initial.amount"),
    countries = Seq("Poland", "France", "USA")
  )

  override val phases: Int = %("test.phases")
}