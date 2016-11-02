package org.dmcs.transaction.analyst.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.UserEvent

/**
  * Created by Zielony on 2016-10-24.
  */
case class UserEvents(usersCreated: Traversable[UserEvent], usersUpdated: Traversable[UserEvent],
                      usersDeleted: Traversable[UserEvent]);