package scala.model.environment.property.realization

import scala.model.environment.property.Range

sealed trait HumidityProperty extends IntegerProperty with Range

object HumidityProperty extends HumidityProperty {
  override val default: Int = 10
  override val maxValue: Int = 100
  override val minValue: Int = 0

  // TODO se implicit limit funziona -> do nothing, altrimenti -> apply con limit
  case class HumidityState(override val value: Int) extends IntegerState {
    override implicit def apply(value: Int): HumidityState = HumidityState(value)
  }
  /*
  override implicit def instantValue(time: Int): Int = maxValue * Math.sin(time % 365) toInt

  override def variation(olderTime: Int, newerTime: Int): Int => Int =
    value => value + instantValue(olderTime) - instantValue(newerTime)*/
}