package org.dmcs.transaction.analytics.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.UserEvent

case class UserEvents(usersCreated: Traversable[UserEvent], usersUpdated: Traversable[UserEvent],
                      usersDeleted: Traversable[UserEvent]);
