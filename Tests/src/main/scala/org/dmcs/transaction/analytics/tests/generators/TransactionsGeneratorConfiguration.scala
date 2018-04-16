package org.dmcs.transaction.analytics.tests.generators

import prv.zielony.proper.model.Bundle
import prv.zielony.proper.inject._
import prv.zielony.proper.converters.auto._

case class TransactionsGeneratorConfiguration(insertionsPerPhase: Long,
                                              withdrawalsPerPhase: Long,
                                              transfersPerPhase: Long)

object TransactionsGeneratorConfiguration {
  def apply()(implicit bundle: Bundle): TransactionsGeneratorConfiguration = new TransactionsGeneratorConfiguration(
    %("generators.transactions.insertions.per.phase"),
    %("generators.transactions.withdrawals.per.phase"),
    %("generators.transactions.transfers.per.phase")
  )
}