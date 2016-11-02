package org.dmcs.transaction.analyst.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.AccountEvent

/**
  * Created by Zielony on 2016-10-24.
  */
case class AccountEvents(accountsCreated: Traversable[AccountEvent], accountsDeleted: Traversable[AccountEvent])
