package scala.model.environment.property.realization

import scala.model.Time
import scala.model.environment.utility.IteratorHelper.RichIterator

sealed trait TemperatureProperty extends IntegerProperty with IntegerTimedProperty

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  override def seasonalTrend: TimedVariationType =  new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val monthlyValue = (0 to 6).iterator.mirror(false).map(_ * MonthlyIncrement).drop(instant month).toSeq.head
      val variation = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}