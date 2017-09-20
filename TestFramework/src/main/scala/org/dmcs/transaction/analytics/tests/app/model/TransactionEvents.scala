package org.dmcs.transaction.analytics.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.TransactionEvent

case class TransactionEvents(insertionEvents: Traversable[TransactionEvent], withdrawalEvents: Traversable[TransactionEvent],
                             transferEvents: Traversable[TransactionEvent]);
