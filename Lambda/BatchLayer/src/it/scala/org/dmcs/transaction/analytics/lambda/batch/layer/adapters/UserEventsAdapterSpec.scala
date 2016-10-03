package org.dmcs.transaction.analytics.lambda.batch.layer.adapters

import java.sql.Timestamp
import java.time.LocalDateTime

import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.lambda.batch.layer.tests._
import org.dmcs.transaction.analytics.lambda.batch.layer.spark.Spark
import org.dmcs.transaction.analytics.lambda.events.UserEventType._
import org.dmcs.transaction.analytics.lambda.events._
import org.scalatest.{ShouldMatchers, WordSpec}

class UserEventsAdapterSpec extends WordSpec with UserEventsAdapter with ShouldMatchers with Spark {

  val data = List(
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample1", "sample1", PersonalData("User1", "User1"), ContactData(), Created),
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample1", "sample1", PersonalData("User1", "User1.1"), ContactData(), Updated),
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample1", "sample1", PersonalData("User1", "User1.1"), ContactData(), Deleted),
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample2", "sample2", PersonalData("User2", "User2"), ContactData(), Created),
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample3", "sample3", PersonalData("User3", "User3"), ContactData(), Created),
    UserEvent(0, Timestamp.valueOf(LocalDateTime.now()), "sample2", "sample2", PersonalData("User2.2", "User2.2"), ContactData(), Created)
  )

  //TODO: From properties
  override val userEventsPath: String = "target/MDS/Users"

  "UserEventsAdapter" should {

    "provide all user events" in withSparkAndParquetData(data, userEventsPath){ implicit sqlContext =>
      withUserEvents { dataset =>
        val events = dataset.collect
        events should contain theSameElementsAs(data)
      }
    }

    "provide all 'Created' user events" in withSparkAndParquetData(data, userEventsPath) { implicit sqlContext =>
      withUsersCreated { dataset =>
        val created = dataset.collect
        created.foreach(event => event.kind should be(UserEventType.Created))
        created should contain theSameElementsAs(data.filter(event => event.kind == UserEventType.Created))
      }
    }

    "provide all 'Updated' user events" in withSparkAndParquetData(data, userEventsPath) { implicit sqlContext =>
      withUsersUpdated { dataset =>
        val updated = dataset.collect
        updated.foreach(event => event.kind should be(UserEventType.Updated))
        updated should contain theSameElementsAs(data.filter(event => event.kind == UserEventType.Updated))
      }
    }

    "provide all 'Deleted' user events" in withSparkAndParquetData(data, userEventsPath) { implicit sqlContext =>
      withUsersDeleted { dataset =>
        val deleted = dataset.collect
        deleted.foreach(event => event.kind should be(UserEventType.Deleted))
        deleted should contain theSameElementsAs(data.filter(event => event.kind == UserEventType.Deleted))
      }
    }
  }

  private def withSparkAndParquetData[R](data: List[UserEvent], path: String)(f: SQLContext => R): R =
    withSpark[R] { implicit sparkContext =>
      withSparkSql[R]{ implicit sqlContext =>
        import sqlContext.implicits._
        withParquetData(data.toDS(), path) { () =>
          f(sqlContext)
        }
      }
    }
}