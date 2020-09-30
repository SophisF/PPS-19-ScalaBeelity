package scala.model.environment.property.realization

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.realization.PressureProperty.seasonalTrend

/**
 * Test for pressure-property
 *
 */
class PressurePropertyTest extends AnyFunSuite {

  test("Pressure-property should have a linear seasonal-trend") {
    assert(seasonalTrend.instantaneous(30).value == seasonalTrend.instantaneous(60).value)
  }

  test("Pressure should not have a seasonal-trend (should have zero variation in the whole year)") {
    val values = (0 to 365).map(seasonalTrend.instantaneous(_).value)
    assert(values.max == values.min)
  }
}
