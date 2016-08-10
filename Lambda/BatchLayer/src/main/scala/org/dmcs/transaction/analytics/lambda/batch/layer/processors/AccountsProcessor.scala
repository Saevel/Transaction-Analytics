package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.{AccountEventsAdapter, UserEventsAdapter}

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountsProcessor extends AccountEventsAdapter with UserEventsAdapter {

  def constructAccounts: Dataset[UserAccount] = {
    withAccountsCreated { accountsCreated =>
      withAccountsDeleted { accountsDeleted =>
        withUsersCreated { usersCreated =>
          withUsersUpdated { usersUpdated =>
            //TODO: Construct with given data
            ???
          }
        }
      }
    }
  }
}
