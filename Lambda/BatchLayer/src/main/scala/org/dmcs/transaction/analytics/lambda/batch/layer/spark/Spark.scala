package org.dmcs.transaction.analytics.lambda.batch.layer.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Encoder, Encoders, SQLContext}
import org.dmcs.transaction.analytics.lambda.events.UserEventType
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load
import prv.zielony.proper.inject._

/**
  * Created by Zielony on 2016-08-02.
  */
trait Spark {

  val sparkProperties: Bundle = load(classpath :/ "spark.properties")

  //TODO: Move to some utils if it works
  implicit val userEventTypeEncoder: Encoder[UserEventType] = Encoders.kryo(classOf[UserEventType])

  def withSpark[T](f: SparkContext => T) = {
    var contextOption: Option[SparkContext] = None;
    try {
      contextOption = Option(
        new SparkContext(
          new SparkConf()
            .setMaster(%("spark.master" @@ sparkProperties))
            .setAppName(%("spark.app.name" @@ sparkProperties))
        )
      )
      f(contextOption.get)
    } finally {
      contextOption.foreach(_.stop())
    }
  }

  def withSparkSql[T](f: SQLContext => T)(implicit sparkContext: SparkContext) = f(new SQLContext(sparkContext))
}