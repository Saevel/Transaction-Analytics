package org.dmcs.transaction.analytics.tests.framework.generators

import scala.concurrent.duration.FiniteDuration
import scala.util.{Success, Try}

trait UnitGenerator extends Generator[Unit] {
  override def generate(implicit timeout: FiniteDuration): Try[Unit] = Success({})
}
