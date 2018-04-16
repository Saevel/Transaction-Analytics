package org.dmcs.transaction.analytics.tests.generators.entities

import org.dmcs.transaction.analytics.tests.model.{EntitiesModel, EventsModel}

trait EntitiesModelFactory extends AccountDataFactory with CashOperationsFactory with UserDataFactory {

  protected def entitiesModel(events: EventsModel): EntitiesModel = EntitiesModel(
    cashOperations(events.transactionEvents),
    accountData(events.userEvents, events.accountEvents, events.transactionEvents),
    userData(events.userEvents)
  )
}
