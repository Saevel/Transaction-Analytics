package org.dmcs.transaction.analytics.tests.app

import com.typesafe.scalalogging.LazyLogging
import org.dmcs.transaction.analytics.tests.framework.converters.{Converter, IdentityConverter}
import org.dmcs.transaction.analytics.tests.framework.data.IdentityDataFormat
import org.dmcs.transaction.analytics.tests.framework.evaluators.UnitEvaluator
import org.dmcs.transaction.analytics.tests.framework.generators.UnitGenerator
import org.dmcs.transaction.analytics.tests.framework.ingestors.{IdleIngestor, SinkType}
import org.dmcs.transaction.analytics.tests.framework.model.{BasicEquallities, Equallity, Test}

import scala.concurrent.Future

object ExampleTest extends Test[Unit, Unit, SinkType.Empty.type, Unit, IdentityDataFormat[Unit]]("ExampleTest", 10, 10)
  with BasicEquallities with LazyLogging with UnitGenerator with UnitEvaluator[Unit] with IdleIngestor[Unit]  {

  import scala.concurrent.duration._

  override protected implicit val converter: Converter[Unit, Unit, IdentityDataFormat[Unit]] = new IdentityConverter[Unit]

  override protected val testTimeout: FiniteDuration = 30 seconds

  override protected val equality: Equallity[Unit] = BasicEquallity[Unit]

  override protected def test(data: Traversable[Unit]): Future[Unit] = Future.successful({})
}