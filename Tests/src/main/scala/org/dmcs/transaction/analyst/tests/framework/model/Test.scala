package org.dmcs.transaction.analyst.tests.framework.model

import org.dmcs.transaction.analyst.tests.framework.matchers._

/**
  * Created by Zielony on 2016-10-24.
  */
case class Test[ModelType](name: String, method: (List[ModelType] => Matcher))
