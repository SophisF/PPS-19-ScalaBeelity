package scala.model.environment.property.realization

import scala.model.environment.property.Range
import scala.model.environment.time.TimeData

sealed trait TemperatureProperty extends IntegerProperty with Range with TimeData[Int]

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  override val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class TemperatureState(value: Int) extends IntegerState {
    override implicit def apply(value: Int): TemperatureState = TemperatureState(value)
  }

  /*
  implicit def instantValue(time: Time): Int = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement)
    .drop(time.month).toSeq.head

  implicit def variation(older: Time, newer: Time): Int = instantValue(newer) - instantValue(older)*/
}