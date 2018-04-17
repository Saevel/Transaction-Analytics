package org.dmcs.transaction.analytics.tests.http

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, ResponseEntity}
import akka.http.scaladsl.unmarshalling.{PredefinedFromEntityUnmarshallers, PredefinedFromStringUnmarshallers, Unmarshal, Unmarshaller}
import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}

class SystemClient(host: String, port: Int)(implicit formatter: DateTimeFormatter) extends PredefinedFromStringUnmarshallers
  with PredefinedFromEntityUnmarshallers {

  implicit val intUnmarshaller: Unmarshaller[ResponseEntity, Int] = stringUnmarshaller.map(_.toInt)

  implicit val doubleUnmarshaller: Unmarshaller[ResponseEntity, Double] = stringUnmarshaller.map(_.toDouble)

  implicit val longUnmarshaller: Unmarshaller[ResponseEntity, Long] = stringUnmarshaller.map(_.toLong)

  def clientsAgeMedian(implicit actorSystem: ActorSystem,
                       ex: ExecutionContext,
                       materializer: Materializer): Future[Int] =
    get[Int](s"http://$host:$port/clients/age/median")

  def userAgeAverage(implicit actorSystem: ActorSystem,
                     ex: ExecutionContext,
                     materializer: Materializer): Future[Double] =
    get[Double](s"http://$host:$port/clients/age/average")

  def activeAccountsFor(country: String)
                       (implicit actorSystem: ActorSystem,
                        materializer: Materializer,
                        ex: ExecutionContext): Future[Long] =
    get[Long](s"http://$host:$port/accounts/country/$country/active")

  def averageAccountBalanceFor(country: String)
                              (implicit actorSystem: ActorSystem,
                               materializer: Materializer,
                               ex: ExecutionContext): Future[Double] =
    get[Double](s"http://$host:$port/accounts/country/$country/balance")

  def averageInsertion(start: Option[LocalDateTime] = None, end: Option[LocalDateTime] = None)
                      (implicit actorSystem: ActorSystem,
                       materializer: Materializer,
                       ex: ExecutionContext): Future[Double] =
    get[Double](s"http://$host:$port/capital/insertions/average".withOptionalInterval(start, end))

  def averageWithdrawal(start: Option[LocalDateTime] = None, end: Option[LocalDateTime] = None)
                       (implicit actorSystem: ActorSystem,
                        materializer: Materializer,
                        ex: ExecutionContext): Future[Double] =
    get[Double](s"http://$host:$port/capital/withdrawals/average".withOptionalInterval(start, end))

  def averageCapitalVariance(start: Option[LocalDateTime] = None, end: Option[LocalDateTime] = None)
                       (implicit actorSystem: ActorSystem,
                        materializer: Materializer,
                        ex: ExecutionContext): Future[Double] =
    get[Double](s"http://$host:$port/capital/variance".withOptionalInterval(start, end))

  private[http] def get[T](path: String)(implicit system: ActorSystem,
                                         materializer: Materializer,
                                         ex: ExecutionContext,
                                         unmarshaller: Unmarshaller[ResponseEntity, T]): Future[T] =
    Http().singleRequest(HttpRequest(method = HttpMethods.GET, uri = path)).flatMap(response => Unmarshal(response.entity).to[T])

  private[http] implicit class UrlWithOptionalInterval(url: String){
    def withOptionalInterval(startOption: Option[LocalDateTime], endOption: Option[LocalDateTime]): String =
      (startOption, endOption) match {
        case (Some(start), Some(end)) => s"$url?start=${formatter.format(start)}&end=${formatter.format(end)}"
        case (Some(start), None) => s"$url?start=${formatter.format(start)}"
        case (None, Some(end)) => s"$url?end=${formatter.format(end)}"
        case other => url
      }
  }
}