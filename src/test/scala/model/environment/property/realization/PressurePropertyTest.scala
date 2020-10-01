package scala.model.environment.property.realization

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.utils.{FilterGenerator, SeasonalBehaviour, TimedFilterGenerator}

/**
 * Test for pressure-property
 * Further tests were made in superclass
 */
class PressurePropertyTest extends AnyFunSuite {

  test("Pressure-property should have a FilterGenerator") {
    assert(PressureProperty.isInstanceOf[FilterGenerator])
  }

  test("Pressure-property should have a TimedFilterGenerator") {
    assert(PressureProperty.isInstanceOf[TimedFilterGenerator])
  }

  test("Pressure-property should NOT have a SeasonalBehaviour") {
    assert(! PressureProperty.isInstanceOf[SeasonalBehaviour])
  }

  // FURTHER TESTS WERE MADE IN SUPERCLASS
}
