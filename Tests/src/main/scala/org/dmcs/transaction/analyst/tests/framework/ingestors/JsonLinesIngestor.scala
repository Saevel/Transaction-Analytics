package org.dmcs.transaction.analyst.tests.framework.ingestors

import java.io.{File, FileWriter, PrintWriter}

import play.api.libs.json.{Json, Writes}

trait JsonLinesIngestor[T] extends Ingestor[T] {

  val ingestionTargetFile: File

  implicit val writes: Writes[T]

  override def ingest(data: Traversable[T]): Unit = withFileWriter{ writer =>
    val jsons = data.map(x => Json.toJson(x))
    jsons.foreach(json =>
      writer.println(Json.stringify(json))
    )
  }

  private[ingestors] def withFileWriter(f: (PrintWriter => Unit)): Unit = {

    var writerOption: Option[PrintWriter] = None
    try {
      if (ingestionTargetFile.exists) {
        ingestionTargetFile.delete
      }
      ingestionTargetFile.getParentFile.mkdirs
      ingestionTargetFile.createNewFile
      if (!ingestionTargetFile.isFile) {
        throw new IllegalArgumentException(s"${ingestionTargetFile.getPath} is not a file")
      }

      writerOption = Option(new PrintWriter(new FileWriter(ingestionTargetFile)))
      writerOption.foreach(writer => f(writer))
    } finally {
      writerOption.foreach(_.close)
    }
  }
}