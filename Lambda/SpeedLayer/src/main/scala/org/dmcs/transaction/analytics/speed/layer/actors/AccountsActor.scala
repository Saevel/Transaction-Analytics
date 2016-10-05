package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.apache.spark.sql.SQLContext
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CountAccountsByCountry, CountAccountsByCountryAndAgeInterval}
import org.dmcs.transaction.analytics.speed.layer.adapters.UserAccountsAdapter
import org.dmcs.transaction.analytics.speed.layer.queries.AccountsQueries

/**
  * Created by Zielony on 2016-08-03.
  */
class AccountsActor(private val accountsPath: String, private val sqlContext: SQLContext)
  extends Actor with AccountsQueries with UserAccountsAdapter {

  implicit val sql: SQLContext = sqlContext

  override val userAccountsPath: String = accountsPath

  override def receive: Receive = {

    case CountAccountsByCountry(country) => withUserAccounts { accounts =>
      sender ! countAccountsByCountry(country)(sql)(accounts)
    }

    case CountAccountsByCountryAndAgeInterval(country, minAge, maxAge) => withUserAccounts { accounts =>
      sender ! countAccountsByCountryAndAge(country, minAge, maxAge)(sql)(accounts)
    }
  }
}
