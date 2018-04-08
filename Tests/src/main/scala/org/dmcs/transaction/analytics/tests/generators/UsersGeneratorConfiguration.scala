package org.dmcs.transaction.analytics.tests.generators

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class UsersGeneratorConfiguration(usersPerPhase: Long,
                                       updatedUsersPercentage: Double,
                                       updatesPerUser: Double)

object UsersGeneratorConfiguration {
  def apply()(implicit bundle: Bundle): UsersGeneratorConfiguration = new UsersGeneratorConfiguration(
    %("generators.users.per.phase"),
    %("generators.users.updated.percentage"),
    %("generators.users.updated.per.user")
  )
}