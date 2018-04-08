package org.dmcs.transaction.analytics.test.framework.components.statistics

trait Statistic[DataType, StatType] {
  def evaluate(data: Seq[DataType]): StatType
}

object Statistic {

  def apply[DataType, StatType: Numeric](f: Traversable[DataType] => StatType): Statistic[DataType, StatType] = new Statistic[DataType, StatType] {
    override def evaluate(data: Seq[DataType]) = f(data)
  }

  def combine[DataType, IntermediateType : Numeric, StatType](flatMapper: DataType => Traversable[IntermediateType],
                                                             evaluator: Statistic[IntermediateType, StatType]): Statistic[DataType, StatType] =
    new Statistic[DataType, StatType] {
      override def evaluate(data: Seq[DataType]) = evaluator.evaluate(data.flatMap(flatMapper))
    }
}
