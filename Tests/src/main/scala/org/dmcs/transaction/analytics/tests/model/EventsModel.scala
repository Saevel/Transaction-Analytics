package org.dmcs.transaction.analytics.tests.model

import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, TransactionEvent, UserEvent}

case class EventsModel(accountEvents: Traversable[AccountEvent],
                       transactionEvents: Traversable[TransactionEvent],
                       userEvents: Traversable[UserEvent])
