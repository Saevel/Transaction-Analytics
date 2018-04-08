package org.dmcs.transaction.analytics.tests.model

import org.dmcs.transaction.analyst.lambda.model.{CashOperation, UserAccount, UserData}

case class EntitiesModel(operations: Traversable[CashOperation],
                         userAccounts: Traversable[UserAccount],
                         userData: Traversable[UserData])
