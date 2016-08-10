package org.dmcs.transaction.analytics.lambda.events

/**
  * Created by Zielony on 2016-08-02.
  */
case class ContactData(phone: Option[String],
                       email: Option[String],
                       address: Option[String],
                       country: Option[String]);