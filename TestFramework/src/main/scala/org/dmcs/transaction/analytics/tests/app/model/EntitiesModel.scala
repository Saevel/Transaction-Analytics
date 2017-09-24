package org.dmcs.transaction.analytics.tests.app.model

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, UserAccount, UserData}

case class EntitiesModel(users: Traversable[UserData],
                         accounts: Traversable[UserAccount],
                         transactions: Traversable[CashOperation],
                         originalAccounts: Traversable[UserAccount] = Traversable.empty[UserAccount])
