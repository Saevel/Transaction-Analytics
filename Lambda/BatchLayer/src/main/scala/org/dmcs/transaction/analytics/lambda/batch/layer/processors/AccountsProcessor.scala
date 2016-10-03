package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.{AccountEventsAdapter, UserEventsAdapter}
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, UserEvent}

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountsProcessor extends AccountEventsAdapter with UserEventsAdapter {

  //TODO: Actual construction, take transfers etc into account
  def constructAccounts(implicit sqlContext: SQLContext): Dataset[UserAccount] = {
    import sqlContext.implicits._

    withAllAccountEvents { (accountsCreated, accountsDeleted) =>
      withAllUserEvents { (usersCreated, usersUpdated, usersDeleted) =>

        val accounts = activeAccounts(accountsCreated, accountsDeleted)
        val users = activeUsers(usersCreated, usersUpdated, usersDeleted)

        accounts.joinWith(users, $"userId" === $"id").map { pair =>
          val (account, user) = pair
          UserAccount(user.id, account.accountId, account.balance, user.contactData.country, user.personalData.age)
        }
      }
    }
  }

  private def withAllAccountEvents[T](f: (Dataset[AccountEvent], Dataset[AccountEvent]) => T)
                                     (implicit sqlContext: SQLContext): T =
    withAccountsCreated[T] { created =>
      withAccountsDeleted[T] { deleted =>
        f(created, deleted)
      }
    }

  private def withAllUserEvents[T](f: (Dataset[UserEvent], Dataset[UserEvent], Dataset[UserEvent]) => T)
                                  (implicit sqlContext: SQLContext): T =
    withUsersCreated[T] { created =>
      withUsersUpdated[T] { updated =>
        withUsersDeleted { deleted =>
          f(created, updated, deleted)
        }
      }
    }

  private def activeAccounts(created: Dataset[AccountEvent], deleted: Dataset[AccountEvent])
                            (implicit sqlContext:SQLContext): Dataset[AccountEvent] = {
    import sqlContext.implicits._
    val createdAndRemoved = created.joinWith(deleted, $"accountId" === $"accountId").map { pair =>
      val (removed, _) = pair
      removed
    }
    created.subtract(createdAndRemoved)
  }

  private def activeUsers(created: Dataset[UserEvent],
                          updated: Dataset[UserEvent],
                          deleted: Dataset[UserEvent])(implicit sqlContext:SQLContext): Dataset[UserEvent] = {
    import sqlContext.implicits._
    val createdButRemoved = created.joinWith(deleted, $"id" === $"id").map { pair =>
      val (removed, _) = pair
      removed
    }
    //TODO: Aggregate correctly
    val latestUpdates = updated.groupBy(_.id).mapGroups((id, iterator) =>
      iterator.reduce { (first, second) =>
        if(first.timestamp.after(second.timestamp)) first else second
      }
    )

    created.subtract(createdButRemoved).joinWith(latestUpdates, $"id" === $"id").map{ pair =>
      val (creation, update) = pair
      update
    }
  }
}