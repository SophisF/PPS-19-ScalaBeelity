package scala.model.environment.property.realization

import scala.model.environment.property.realization
import scala.model.environment.time.Time

sealed trait HumidityProperty extends IntegerProperty {
  trait HumidityState extends IntegerState
  trait HumidityVariation extends IntegerVariation
  trait HumidityTimedVariation extends IntegerTimedVariation
}

object HumidityProperty extends HumidityProperty {
  val default: Int = 10
  override val maxValue: Int = 100
  override val minValue: Int = 0

  implicit def state(_value: Int): HumidityState = new HumidityState { override val value: Int = limit(_value) }

  implicit def variation(_value: Int): HumidityVariation = new HumidityVariation {
    val value: Int = _value
    override def vary[S <: State](_state: S): HumidityState = state(_state.value + _value)
  }

  implicit def timedVariation(value: Int, start: Time, duration: Time): HumidityTimedVariation =
    new HumidityTimedVariation {
      override def instantaneous(instant: Time): HumidityVariation = instantaneous(value, start, duration, instant)
    }
  /*
  override implicit def instantValue(time: Int): Int = maxValue * Math.sin(time % 365) toInt

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
  def seasonalTrend(instant: Time = Time.now()): HumidityTimedVariation = new HumidityTimedVariation {
    private var lastGet: Int = 0
    override def instantaneous(instant: Time): HumidityVariation = {
      val monthlyValue = maxValue * Math.sin(instant % 365) toInt
      val variation = monthlyValue - lastGet
      lastGet = monthlyValue
      variation
    }
  }
}