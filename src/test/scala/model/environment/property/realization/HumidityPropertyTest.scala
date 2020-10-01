package scala.model.environment.property.realization

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.realization.HumidityProperty.seasonalTrend
import scala.model.environment.property.utils.{FilterGenerator, SeasonalBehaviour, TimedFilterGenerator}

/**
 * Test for humidity-property
 * Further tests were made in superclass
 */
class HumidityPropertyTest extends AnyFunSuite {

  test("Humidity-property should have a non-linear seasonal-trend") {
    assert(seasonalTrend.instantaneous(30).value != seasonalTrend.instantaneous(60).value)
  }

  test("Humidity seasonal-trend should be equal in the same period of two different years") {
    assert(seasonalTrend.instantaneous(30).value == seasonalTrend.instantaneous(365 + 30).value)
  }

  test("Variations in the seasonal-trend should not be greater than the humidity values range") {
    val values = (0 to 365).map(seasonalTrend.instantaneous(_).value)
    assert(values.max - values.min <= HumidityProperty.maxValue - HumidityProperty.minValue)
  }

  test("Humidity-property should have a FilterGenerator") {
    assert(HumidityProperty.isInstanceOf[FilterGenerator])
  }

  test("Humidity-property should have a TimedFilterGenerator") {
    assert(HumidityProperty.isInstanceOf[TimedFilterGenerator])
  }

  test("Humidity-property should have a SeasonalBehaviour") {
    assert(HumidityProperty.isInstanceOf[SeasonalBehaviour])
  }

  // FURTHER TESTS WERE MADE IN SUPERCLASS
}
