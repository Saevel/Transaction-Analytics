package org.dmcs.transaction.analytics.tests.generators.entities

import java.sql.Timestamp

import org.dmcs.transaction.analyst.lambda.model.UserData
import org.dmcs.transaction.analytics.lambda.events.{UserEvent, UserEventType}
import org.dmcs.transaction.analytics.tests.model.{EntitiesModel, EventsModel}

trait UserDataFactory {

  implicit val timestampOrdering = new Ordering[Timestamp] {
    override def compare(x: Timestamp, y: Timestamp) = x.compareTo(y)
  }

  private[generators] def userData(events: Traversable[UserEvent]): Traversable[UserData] =
    events
        .filter(creationEventsWithAge)
        .map(event => UserData(event.id, event.personalData.age.get))
        .filterNot(deletedUsers(events))
        .map(user => withUpdatedAge(events.filter(updatesFor(user)))(user))

  private[generators] def updatesFor(data: UserData)(event: UserEvent): Boolean =
    event.kind == UserEventType.Updated && event.id == data.userId

  private[generators] def withUpdatedAge(updates: Traversable[UserEvent])(data: UserData): UserData =
    updates.toSeq.sortBy(_.timestamp).foldLeft(data)(updateAge)

  private[generators] def updateAge(data: UserData, update: UserEvent): UserData =
    update.personalData.age.fold(data)(newAge => data.copy(age = newAge))

  private[generators] def deletedUsers(events: Traversable[UserEvent])(users: UserData): Boolean =
    events.filter(_.kind == UserEventType.Deleted).exists(_.id == users.userId)

  // private[generators] def updateUserAge(updates: Traversable[UserEvent])(user: UserData): UserData =

  private[generators] def creationEventsWithAge(event: UserEvent): Boolean = (event.kind == UserEventType.Created
    && event.personalData.age.isDefined)
}