package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.{Dataset, SQLContext}
import org.apache.spark.sql.functions._

import org.dmcs.transaction.analyst.lambda.model.UserAccount
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.{AccountEventsAdapter, TransactionEventsAdapter, UserEventsAdapter}
import org.dmcs.transaction.analytics.lambda.events.{AccountEvent, TransactionEvent, UserEvent}

trait AccountsProcessor extends AccountEventsAdapter with UserEventsAdapter with TransactionEventsAdapter {

  def constructAccounts(implicit sqlContext: SQLContext): Dataset[UserAccount] = {
    import sqlContext.implicits._

    withAllAccountEvents { (accountsCreated, accountsDeleted) =>
      withAllUserEvents { (usersCreated, usersUpdated, usersDeleted) =>
        withAllTransactionEvents { (transfers, withdrawals, insertions) =>
          val accounts = activeAccounts(accountsCreated, accountsDeleted)
          val users = activeUsers(usersCreated, usersUpdated, usersDeleted)
          val balances = balanceChanges(transfers, withdrawals, insertions)

          accounts.joinWith(users, $"userId" === $"id").joinWith(balances, $"userId" === $"id").map {
            case ((account, user), (id, delta)) =>
              new UserAccount(id, account.accountId, account.balance + delta, user.contactData.country,
                user.personalData.age)
          }
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

  private def withAllTransactionEvents[T](f: (Dataset[TransactionEvent], Dataset[TransactionEvent],
     Dataset[TransactionEvent]) => T)(implicit sqlContext: SQLContext): T =
    withWithdrawalEvents[T] { withdrawals =>
      withInsertionEvents[T] { insertions =>
        withTransferEvents[T] { transfers =>
          f(transfers, withdrawals, insertions)
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

    val latestUpdates = updated.groupBy(_.id).mapGroups((id, iterator) =>
      iterator.reduce { (first, second) =>
        if(first.timestamp.after(second.timestamp)) first else second
      }
    )

    created.subtract(createdButRemoved).joinWith(latestUpdates, $"id" === $"id").map{ pair =>
      val (_, update) = pair
      update
    }
  }

  //TODO: Refactor heavily
  private def balanceChanges(transfers: Dataset[TransactionEvent],
                             withdrawals: Dataset[TransactionEvent],
                             insertions: Dataset[TransactionEvent])
                            (implicit sqlContext: SQLContext): Dataset[(Long, Double)] = {
    import sqlContext.implicits._

    val insertionsByAccount = insertions.groupBy(_.sourceAccount).mapGroups {
      case (id, iterator) => (id, iterator.map(_.amount).reduce(_+_))
    }

    val withdrawalsByAccount = withdrawals.groupBy(_.sourceAccount).mapGroups{
      case (id, iterator) => (id, iterator.map(_.amount).reduce(_+_))
    }

    val transferSources = transfers.filter(_.targetAccount.isDefined).groupBy(_.sourceAccount).mapGroups {
      case (id, iterator) => (id, iterator.map(_.amount).reduce(_+_))
    }

    val transferTargets = transfers.filter(_.targetAccount.isDefined).groupBy(_.targetAccount.get).mapGroups{
      case (id, iterator) => (id, iterator.map(_.amount).reduce(_+_))
    }

    val allLosses = withdrawalsByAccount.joinWith(transferSources, $"id" === $"id").map{
      case ((id, s1),(id2, s2)) => (id, (-1)*(s1 + s2))
    }

    val allGains = insertionsByAccount.joinWith(transferTargets, $"id" === $"id").map{
      case ((id1, s1),(id2, s2)) => (id1, s1 + s2)
    }

    allGains.joinWith(allLosses, $"id" === $"id").map {
      case ((id, s1), (id2, s2)) => (id, s1 + s2)
    }
  }
}