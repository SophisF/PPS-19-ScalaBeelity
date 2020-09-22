package scala.model.environment.property.realization

import scala.model.Time
import scala.utility.IterableHelper.RichIterable

sealed trait TemperatureProperty extends IntegerProperty with IntegerTimedProperty

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 2

  override val default: Int = 20
  override val maxValue: Int = 40
  override val minValue: Int = -10

  override def seasonalTrend: TimedVariationType =  new TimedVariationType {
    private var lastGet: Int = 0

    override def instantaneous(instant: Time): VariationType = {
      val monthlyValue = (0 to 6).mirror(false).map(_ * MonthlyIncrement).drop(instant month).head
      val variation = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}