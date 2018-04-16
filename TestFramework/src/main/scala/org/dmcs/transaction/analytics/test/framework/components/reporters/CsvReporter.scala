package org.dmcs.transaction.analytics.test.framework.components.reporters

import java.io.{File, FileOutputStream, FileWriter, PrintWriter}
import java.time.format.DateTimeFormatter

import org.dmcs.transaction.analytics.test.framework.TestResult

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class CsvReporter(val targetFile: File) extends Reporter {

  protected val formatter = DateTimeFormatter.ISO_DATE_TIME

  override def report(results: Traversable[TestResult[_]])(implicit ex: ExecutionContext): Future[_] = Future {
    val tryWriter = Try(new FileWriter(targetFile))

    val tryResult = tryWriter.map { writer =>
      results.foreach { result =>
        writer.write(stringify(result))
        writer.write('\n')
      }
    }

    tryResult match {
      case Success(_) => {
        tryWriter.foreach(_.close)
        Future.successful({})
      }
      case Failure(e) => {
        tryWriter.foreach(_.close)
        Future.failed(e)
      }
    }
  }

  private[reporters] def stringify(result: TestResult[_]): String =
    s"${result.testName},${result.phaseId},${result.expected},${result.actual},${result.passed},${formatter.format(result.startTimestamp)},${formatter.format(result.endTimestamp)}"
}