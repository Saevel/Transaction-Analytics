package org.dmcs.transaction.analyst.tests.app.model

import org.dmcs.transaction.analytics.lambda.events.TransactionEvent

/**
  * Created by Zielony on 2016-10-24.
  */
case class TransactionEvents(insertionEvents: Traversable[TransactionEvent], withdrawalEvents: Traversable[TransactionEvent],
                             transferEvents: Traversable[TransactionEvent]);
