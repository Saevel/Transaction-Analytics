package org.dmcs.transaction.analytics.tests.generators.events.users

import prv.zielony.proper.converters.auto._
import prv.zielony.proper.inject._
import prv.zielony.proper.model.Bundle

case class UsersGeneratorConfiguration(usersPerPhase: Long,
                                       updatedUsersPercentage: Double,
                                       updatesPerUser: Double,
                                       countries: Seq[String],
                                       deletedUsersPercentage: Double)

object UsersGeneratorConfiguration {
  def apply()(implicit bundle: Bundle): UsersGeneratorConfiguration = new UsersGeneratorConfiguration(
    %("generators.users.per.phase"),
    %("generators.users.updated.percentage"),
    %("generators.users.updates.per.user"),
    %("generators.users.countries").split(",").toSeq,
    %("generators.users.deleted.percentage")
  )
}