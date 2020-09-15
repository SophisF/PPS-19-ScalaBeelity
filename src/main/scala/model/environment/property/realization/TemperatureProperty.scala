package scala.model.environment.property.realization

import scala.model.environment.time.{Time, TimeData}
import scala.model.environment.utility.IteratorHelper.RichIterator

sealed trait TemperatureProperty extends IntegerProperty with TimeData[Int] {
  trait TemperatureState extends IntegerState
  trait TemperatureVariation extends IntegerVariation
  trait TemperatureTimedVariation extends IntegerTimedVariation
}

object TemperatureProperty extends TemperatureProperty {
  private val MonthlyIncrement = 3

  val default: Int = 0
  override val maxValue: Int = 50
  override val minValue: Int = -50

  implicit def state(_value: Int): TemperatureState = new TemperatureState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): TemperatureVariation = new TemperatureVariation {
    val value: Int = _value
    override def vary[S <: State](_state: S): TemperatureState = state(_state.value + _value)
  }

  implicit def timedVariation(value: Int, start: Time, duration: Time): TemperatureTimedVariation =
    new TemperatureTimedVariation {
      override def instantaneous(instant: Time): TemperatureVariation = instantaneous(value, start, duration, instant)
    }
  /*
  implicit def instantValue(time: Time): Int = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement)
    .drop(time.month).toSeq.head

  implicit def variation(older: Time, newer: Time): Int = instantValue(newer) - instantValue(older)*/
  override def seasonalTrend(instant: Time): TemperatureTimedVariation =  new TemperatureTimedVariation {
    private var lastGet: Int = 0
    override def instantaneous(instant: Time): TemperatureVariation = {
      val monthlyValue = (0 until 6).iterator.mirror(false).map(_ * MonthlyIncrement).drop(instant month).toSeq.head
      val variation = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}