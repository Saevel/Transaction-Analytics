package org.dmcs.transaction.analytics.speed.layer.queries

import org.apache.spark.sql.{Dataset, SQLContext}
import org.dmcs.transaction.analyst.lambda.model.UserAccount

/**
  * Created by Zielony on 2016-08-03.
  */
trait AccountsQueries {

  def countAccountsByCountry(country: String)(implicit sqlContext: SQLContext) = withCountry(country) { accounts =>
    accounts.count
  }

  def countAccountsByCountryAndAge(country: String, minAge: Option[Int], maxAge:Option[Int])
                                  (implicit sqlContext: SQLContext) =
    withCountry(country)(withAgeInterval(minAge, maxAge) ( accounts => accounts.count))

  private[queries] def withCountry[T](country: String)(f: (Dataset[UserAccount] => T))
                                     (implicit sqlContext: SQLContext): (Dataset[UserAccount] => T) = { dataset =>
    f(dataset.filter(_.country.fold(false)(_ == country)))
  }

  private[queries] def withAgeInterval[T](minAge: Option[Int], maxAge:Option[Int])(f: (Dataset[UserAccount] => T))
                                         (implicit sqlContext: SQLContext):(Dataset[UserAccount] => T) = { accounts =>
    f((minAge, maxAge) match {
        //TODO: Forall default'owo zwraca true dla pustego Option =(
        case (Some(min), Some(max)) => accounts.filter( acc => acc.age.fold(false)(age => age >= min && age <= max))
        case (Some(min), None) => accounts.filter(account => account.age.fold(false)(_ >= min))
        case (None, Some(max)) => accounts.filter(account => account.age.fold(false)(_ <= max))
        case _ => accounts
      }
    )
  }
}
