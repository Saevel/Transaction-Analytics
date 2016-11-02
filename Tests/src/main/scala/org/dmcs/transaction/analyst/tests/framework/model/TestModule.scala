package org.dmcs.transaction.analyst.tests.framework.model

/**
  * Created by Zielony on 2016-10-26.
  */
case class TestModule[TestModel](name: String, var tests: List[Test[TestModel]])
