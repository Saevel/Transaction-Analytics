package org.dmcs.transaction.analytics.speed.layer.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.dmcs.transaction.analytics.speed.layer.actors.commands.{CountAccountsByCountry, CountAccountsByCountryAndAgeInterval}
import org.dmcs.transaction.analytics.speed.layer.adapters.UserAccountsAdapter
import org.dmcs.transaction.analytics.speed.layer.queries.AccountsQueries

/**
  * Created by Zielony on 2016-08-03.
  */
class AccountsActor extends Actor with AccountsQueries with UserAccountsAdapter {
  override def receive: Receive = {

    case CountAccountsByCountry(country) => withUserAccounts { accounts =>
      sender ! countAccountsByCountry(country)(accounts)
    }

    case CountAccountsByCountryAndAgeInterval(country, minAge, maxAge) =>
      sender ! countAccountsByCountryAndAge(country, minAge, maxAge)

  }
}
