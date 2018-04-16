package org.dmcs.transaction.analytics.tests.generators.events.users

import java.sql.Timestamp
import java.time.LocalDateTime

import org.dmcs.transaction.analytics.lambda.events.{ContactData, PersonalData, UserEvent, UserEventType}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

trait UserEventsGenerator {

  private val names: Gen[String] = Gen.oneOf("Adam", "John", "Edward", "Francis", "Thomas", "Richard", "Peter", "Paul",
    "James", "George", "William", "Ronald", "Donald", "Robert", "Anna", "Catherine", "Barbara", "Tamara", "Melania",
    "Emma", "Sophia", "Isabella", "Zoe", "Charlotte", "Lilly", "Emily")

  private val ages = Gen.option(Gen.choose(18, 90))

  private def contactData(countries: Seq[String]): Gen[ContactData] = for {
    phoneNumber <- Gen.option(phoneNumbers)
    address <- Gen.option(Gen.alphaStr)
    email <- Gen.option(emails)
    country <- Gen.oneOf(countries)
  } yield ContactData(phoneNumber, email, address, Some(country))

  private val surnames: Gen[String] = Gen.oneOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Wilson")

  private val phoneNumbers: Gen[String] = Gen.choose(100000000, 999999999).map(_.toString)

  private val emails: Gen[String] = for {
    mail <- Gen.alphaStr
    domain <- Gen.alphaStr
  } yield s"$mail@$domain.com"

  private val personalData: Gen[PersonalData] = for {
    name <- names
    surname <- surnames
    age <- ages
  } yield PersonalData(name, surname, age)

  protected def userEventsGenerator(configuration: UsersGeneratorConfiguration): Gen[Traversable[UserEvent]] = for {
    creationEvents <- createdEventsGenerator(configuration)
    updateEvents <- updatedEventsGenerator(configuration, creationEvents)
    deletionEvents <- deleteEventsGenerator(configuration, creationEvents)
  } yield creationEvents ++ updateEvents ++ deletionEvents

  private[generators] def createdEventsGenerator(configuration: UsersGeneratorConfiguration): Gen[List[UserEvent]] =
    Gen.listOfN(configuration.usersPerPhase.toInt, for {
      id <- arbitrary[Long].map(Math.abs)
      username <- Gen.alphaStr
      password <- Gen.alphaStr
      personal <- personalData
      contact <- contactData(configuration.countries)
    } yield UserEvent(id, Timestamp.valueOf(LocalDateTime.now), username, password, personal, contact, UserEventType.Created))

  import org.dmcs.transaction.analytics.tests.generators.events.users.UserFields._

  private[generators] def updatedEventsGenerator(configuration: UsersGeneratorConfiguration, creationEvents: Seq[UserEvent]): Gen[List[UserEvent]] =
    Gen.listOfN(Math.floor(configuration.usersPerPhase * configuration.updatedUsersPercentage).toInt, for {
      creationEvent <- Gen.oneOf[UserEvent]( xs = creationEvents)
      fieldChanged <- Gen.oneOf(UserFields.values.toSeq)
      event <- fieldChangeEvent(creationEvent, configuration.countries, fieldChanged)
    } yield event)

  private[generators] def fieldChangeEvent(creation: UserEvent, countries: Seq[String], fieldChanged: UserFields.Value): Gen[UserEvent] = fieldChanged match {
    case Username => Gen.alphaStr.map(UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), _, creation.password,
      creation.personalData, creation.contactData, UserEventType.Updated))
    case Password => Gen.alphaStr.map(UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, _,
      creation.personalData, creation.contactData, UserEventType.Updated))
    case Name => names.map( newName => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData.copy(name = newName), creation.contactData, UserEventType.Updated))
    case Surname => surnames.map(newSurname => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData.copy(surname = newSurname), creation.contactData, UserEventType.Updated))
    case Age => ages.map(newAge => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData.copy(age = newAge), creation.contactData, UserEventType.Updated))
    case PhoneNumber => Gen.option(phoneNumbers).map(newNumber => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData, creation.contactData.copy(phone = newNumber), UserEventType.Updated))
    case Email => Gen.option(emails).map(newEmail => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData, creation.contactData.copy(email = newEmail), UserEventType.Updated))
    case Address => Gen.option(Gen.alphaStr).map(newAddress => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData, creation.contactData.copy(address = newAddress), UserEventType.Updated))
    case Country => Gen.option(Gen.oneOf(countries)).map(newCountry => UserEvent(creation.id, Timestamp.valueOf(LocalDateTime.now), creation.username, creation.password,
      creation.personalData, creation.contactData.copy(country = newCountry), UserEventType.Updated))
  }

  private[generators] def deleteEventsGenerator(configuration: UsersGeneratorConfiguration, creationEvents: Seq[UserEvent]): Gen[List[UserEvent]] =
    Gen.listOfN(
      Math.floor(configuration.usersPerPhase * configuration.deletedUsersPercentage).toInt,
      Gen.oneOf(creationEvents)
    ) map { events => events map { event =>
      UserEvent(event.id, Timestamp.valueOf(LocalDateTime.now), event.username, event.password, event.personalData, event.contactData, UserEventType.Deleted)
    }}
}