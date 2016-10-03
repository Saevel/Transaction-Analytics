package org.dmcs.transaction.analytics.test.clients

import java.time.LocalDateTime

import akka.actor.ActorSystem
import spray.client.pipelining._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

/**
  * Created by Zielony on 2016-08-04.
  */
class TransactionAnalystClient(sendReceive: SendReceive, host: String, port: Int) {

  private val pipeline = sendReceive

  private val base = s"$host:$port"

  def activeAccountsByCountry(country: String)
                             (implicit executionContext: ExecutionContext): Future[Either[Throwable, Long]] =
    restQuery(s"$base/accounts/country/$country/active")(_.toLong)

  def averageAccountBalanceByCountryAndAge(country: String,
                                           minAge: Option[Int],
                                           maxAge: Option[Int])
                                          (implicit executionContext: ExecutionContext): Future[Either[Throwable, Double]] =
    restQuery(s"$base/accounts/country/$country/balance" ? ("minAge" -> minAge) ? ("maxAge" -> maxAge))(_.toDouble)

  def capitalVarianceByPeriod(startDate: Option[LocalDateTime],
                              endDate: Option[LocalDateTime])
                             (implicit executionContext: ExecutionContext): Future[Either[Throwable, Double]] =
    restQuery(s"$base/capital/variance" ? ("start" -> startDate) ? ("end" -> endDate))(_.toDouble)

  def averageInsertionByPeriod(startDate: Option[LocalDateTime],
                               endDate: Option[LocalDateTime])
                              (implicit executionContext: ExecutionContext): Future[Either[Throwable, Double]] =
    restQuery(s"$base/capital/insertions/average" ? ("start" -> startDate) ? ("end" -> endDate))(_.toDouble)

  def averageWithdrawalByPeriod(startDate: Option[LocalDateTime],
                                endDate: Option[LocalDateTime])
                               (implicit executionContext: ExecutionContext): Future[Either[Throwable, Double]] =
    restQuery(s"$base/capital/withdrawals/average" ? ("start" -> startDate) ? ("end" -> endDate))(_.toDouble)

  def clientAgeMedian(implicit executionContext: ExecutionContext): Future[Either[Throwable, Int]] = restQuery(s"$base/clients/age/median")(_.toInt)

  def clientAgeAverage(implicit executionContext: ExecutionContext): Future[Either[Throwable, Double]] = restQuery(s"$base/clients/age/average")(_.toDouble)

  private implicit class UrlWithQueryParam(baseUrl: String) {

    def ?[T](param: (String, Option[T])) = {
      val (key, option) = param
      option.fold(baseUrl) { value =>
        if(baseUrl contains "&") {
          s"$baseUrl?$key=$value"
        }
        else {
          s"$baseUrl&$key=$value"
        }
      }
    }
  }

  /**
    * Executes a REST query to the given URL and pre-processes the result to indicate success or failure.
    *
    * @param url the target URL.
    * @param convert converts the response body to the final type
    * @tparam T the response type.
    * @return a <code>Future</code> evaluating either to a <code>Throwable</code> in case of exception
    *         while pre-processing the request or the desired result.
    */
  private def restQuery[T](url: String)(convert: (String => T))(implicit executionContext:ExecutionContext):
    Future[Either[Throwable, T]] = try {
      pipeline(Get(url)).map(response => Right[Throwable, T](convert(response.entity.asString)))
    } catch {
      case NonFatal(e) => Future(Left[Throwable, T](e))
    }
}

object TransactionAnalystClient {

  def apply(host: String = "localhost", port: Int = 80)
           (implicit actorSystem: ActorSystem, executionContext: ExecutionContext) = {
    val pipeline = sendReceive
    new TransactionAnalystClient(pipeline, host, port)
  }
}