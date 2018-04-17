package org.dmcs.transaction.analytics.test.framework

import org.dmcs.transaction.analytics.test.framework.components.generators.{GenerationTimeout, Generator}
import org.dmcs.transaction.analytics.test.framework.components.ingestors.Ingestor
import org.dmcs.transaction.analytics.test.framework.components.reporters.Reporter
import org.dmcs.transaction.analytics.test.framework.utils._

import scala.concurrent.{ExecutionContext, Future}

class TestRunner[DataType](reporter: Reporter, generator: Generator[DataType], ingestor: Ingestor[DataType], phaseCount: Int) {

  def runAll(tests: Seq[DataDrivenTest[DataType, _]])
            (implicit ex: ExecutionContext, generationTimeout: GenerationTimeout): Future[_] = {
    (0 until phaseCount)
      .map(_ => generator.generate)
      .foldSequentially
      .flatMap(allData => allData.zipWithIndex.map { case (currentPhaseData, phaseId) =>
        ingestor.ingest(currentPhaseData, phaseId)
        runPhase(tests, allData.untilPhase(phaseId), currentPhaseData, phaseId)
      }.foldSequentially)
      .map(_.flatten)
      .flatMap(reporter.report)
  }

  private[framework] def runPhase(tests: Seq[DataDrivenTest[DataType, _]],
                                  formerPhasesData: Seq[DataType],
                                  currentPhaseData: DataType,
                                  phaseId: Int)(implicit ex: ExecutionContext): Future[Seq[TestResult[_]]] =
    tests
    .map(runTest(formerPhasesData, currentPhaseData, phaseId))
    .foldSequentially

  private[framework] def runTest(formerPhasesData: Seq[DataType],
                                 currentPhaseData: DataType,
                                 phaseId: Int)
                                (test: DataDrivenTest[DataType, _])
                                (implicit ex: ExecutionContext): Future[TestResult[_]] =
    test.execute(phaseId, formerPhasesData, currentPhaseData)


  private[framework] implicit class FormerPhasesData[T](allData: Seq[T]){
    def untilPhase(phaseId: Int) = allData.take(phaseId).foldLeft(Seq.empty[T])(_ :+ _)
  }
}