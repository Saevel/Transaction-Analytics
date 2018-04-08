package org.dmcs.transaction.analytics.tests.generators

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class AccountsGeneratorConfiguration(accountsPerUser: Double,
                                          deletedAccountsPercentage: Double)

object AccountsGeneratorConfiguration {
  def apply()(implicit bundle: Bundle): AccountsGeneratorConfiguration = new AccountsGeneratorConfiguration(
    %("generators.accounts.per.user"),
    %("generators.accounts.deleted.percentage")
  )
}