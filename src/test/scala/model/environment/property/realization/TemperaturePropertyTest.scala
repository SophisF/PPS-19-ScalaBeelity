package scala.model.environment.property.realization

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.realization.TemperatureProperty.seasonalTrend

/**
 * Test for temperature-property
 */
class TemperaturePropertyTest extends AnyFunSuite {

  test("Temperature-property should have a non-linear seasonal-trend") {
    assert(seasonalTrend.instantaneous(30).value != seasonalTrend.instantaneous(60).value)
  }

  test("Temperature seasonal-trend should be equal in the same period of two different years") {
    assert(seasonalTrend.instantaneous(30).value == seasonalTrend.instantaneous(365 + 30).value)
  }

  test("Variations in the seasonal-trend should not be greater than the temperature values range") {
    val values = (0 to 365).map(seasonalTrend.instantaneous(_).value)
    assert(values.max - values.min <= TemperatureProperty.maxValue - TemperatureProperty.minValue)
  }
}
