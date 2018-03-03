package org.dmcs.transaction.analytics.tests.app

import org.dmcs.transaction.analytics.tests.app.generators.TestModelGenerator
import org.dmcs.transaction.analytics.tests.app.model.TestModel
import org.dmcs.transaction.analytics.tests.framework.data.AvroDataFormat
import org.dmcs.transaction.analytics.tests.framework.ingestors.SinkType
import org.dmcs.transaction.analytics.tests.framework.model.Test

// TODO: Make this project all about the framework, refactor tests for respective systems to separate projects, with this as a dependency

class AverageCapitalVarianceTest(iterations: Int, size: Int) /* extends Test[TestModel, Double, SinkType.Hdfs.type, Array[Byte], AvroDataFormat]("Average capital variance for Lambda Architecture", iterations, size)
  with GlobalExecutionContext with TestModelGenerator {

  /**
    * Pomysł: Dane muszą być albo do usunięcia po teście (najlepiej) albo testy powinny być na całości systemu tj
    *  1. Generujemy dane do fazy
    *  2. Wykonujemy wszystkie testy dla tej fazy
    *  3. Przejście do kolejnej fazy
    *
    *  Zalety: Testowanie na większym zakresie danych
    *  Wady: Wolniejsze (cleanup gorszy niż opóźnienia ze względu na sporą ilość danych), wszystkie testy silnie sprzężone
    */
}
*/
