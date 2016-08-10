package org.dmcs.transaction.analytics.test.generator

/**
  * Created by Zielony on 2016-08-04.
  */
trait Generator[GeneratedType] {

  def next: GeneratedType

}