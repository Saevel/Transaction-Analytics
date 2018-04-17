package org.dmcs.transaction.analytics.lambda.speed.layer.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import prv.zielony.proper.inject._
import prv.zielony.proper.model.Bundle
import prv.zielony.proper.path.classpath
import prv.zielony.proper.utils.load

/**
  * Created by Zielony on 2016-08-02.
  */
trait Spark {

  val sparkProperties: Bundle = load(classpath :/ "spark.properties")

  def withSpark[T](f: SparkContext => T): T = {
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

  def withSparkSql[T](f: SQLContext => T)(implicit sparkContext: SparkContext): T = f(new SQLContext(sparkContext))
}
