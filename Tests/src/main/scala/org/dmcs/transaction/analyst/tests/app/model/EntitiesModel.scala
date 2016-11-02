package org.dmcs.transaction.analyst.tests.app.model

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, UserAccount, UserData}

/**
  * Created by Zielony on 2016-10-24.
  */
case class EntitiesModel(users: Traversable[UserData],
                         accounts: Traversable[UserAccount],
                         transactions: Traversable[CashOperation],
                         originalAccounts: Traversable[UserAccount] = Traversable.empty[UserAccount])
