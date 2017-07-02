package org.dmcs.transaction.analytics.lambda.batch.layer.processors

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.lambda.batch.layer.adapters.UserEventsAdapter
import org.dmcs.transaction.analytics.lambda.events.UserEvent

trait UsersProcessor extends UserEventsAdapter {

  def constructUsers(implicit sqlContext: SQLContext): Dataset[UserData] = withAllUserEvents { (created, updated, deleted) =>
    import sqlContext.implicits._
    activeUsers(created, updated, deleted).filter(_.personalData.age.isDefined).map { latest =>
      UserData(latest.id, latest.personalData.age.get)
    }
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

    // TODO: Correct
    created.subtract(createdButRemoved).joinWith(latestUpdates, $"id" === $"id").map{ pair =>
      val (_, update) = pair
      update
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
}
