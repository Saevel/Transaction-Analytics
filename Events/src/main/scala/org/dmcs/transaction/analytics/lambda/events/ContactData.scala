package org.dmcs.transaction.analytics.lambda.events

/**
  * Created by Zielony on 2016-08-02.
  */
case class ContactData(phone: Option[String] = None,
                       email: Option[String] = None,
                       address: Option[String] = None,
                       country: Option[String] = None);