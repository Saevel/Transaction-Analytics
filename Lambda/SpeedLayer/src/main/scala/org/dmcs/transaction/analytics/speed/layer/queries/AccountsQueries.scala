package org.dmcs.transaction.analytics.speed.layer.queries

import org.apache.spark.sql.Dataset
import org.dmcs.transaction.analyst.lambda.model.UserAccount

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountsQueries {

  def countAccountsByCountry(country: String) = withCountry(country) { accounts =>
    accounts.count
  }

  def countAccountsByCountryAndAge(country: String, minAge: Option[Int], maxAge:Option[Int]) = withCountry(country) {
    withAgeInterval(minAge, maxAge) { accounts =>
      accounts.count
    }
  }

  private[queries] def withCountry[T](country: String)
                                     (f: (Dataset[UserAccount] => T)):(Dataset[UserAccount] => T) = { dataset =>
    f(dataset.filter(_.country == country))
  }

  private[queries] def withAgeInterval[T](minAge: Option[Int], maxAge:Option[Int])
                                         (f: (Dataset[UserAccount] => T)):(Dataset[UserAccount] => T) = { accounts =>
    f(
      (minAge, maxAge) match {
        case (Some(min), Some(max)) => accounts.filter(acc => acc.age >= min && acc.age >= max)
        case (Some(min), None) => accounts.filter(_.age >= min)
        case (None, Some(max)) => accounts.filter(_.age <= max)
        case _ => accounts
      }
    )
  }
}
