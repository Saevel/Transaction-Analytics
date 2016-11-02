package org.dmcs.transaction.analyst.tests.framework.reporters

import kantan.csv._
import kantan.csv.ops._

import org.dmcs.transaction.analyst.tests.framework.model.TestResult

trait CsvReporter extends Reporter {

  val separator: Char
  
  def report(results: Traversable[TestResult]): Unit = withCsvWriter { writer =>
    results.foreach(writer.write)
  }
  
  private[reporters] def withCsvWriter(f: (CsvWriter[TestResult] => Unit)) = {
    var csvWriterOption: Option[CsvWriter[TestResult]] = None
    
    try {

      if (reportOutput.exists) {
        reportOutput.delete()
      }
      reportOutput.createNewFile()
      if (!reportOutput.isFile) {
        throw new IllegalArgumentException(s"${reportOutput.getPath} is not a file")
      }
      
      csvWriterOption = Option(reportOutput.asCsvWriter[TestResult](separator))
      csvWriterOption.foreach(f)
    } finally {
      csvWriterOption.foreach(_.close())
    }
  }

  implicit val resultEncoder = new RowEncoder[TestResult] {
    override def encode(result: TestResult): Seq[String] = Seq(
      result.name, result.phase.toString, result.isSuccessful.toString, result.nanoTime.toString
    )
  }
}