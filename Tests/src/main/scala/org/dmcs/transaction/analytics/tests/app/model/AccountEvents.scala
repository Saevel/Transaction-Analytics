package org.dmcs.transaction.analytics.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.AccountEvent

case class AccountEvents(accountsCreated: Traversable[AccountEvent], accountsDeleted: Traversable[AccountEvent])
