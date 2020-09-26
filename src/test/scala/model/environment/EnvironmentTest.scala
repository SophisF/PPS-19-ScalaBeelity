package scala.model.environment

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.source.ZoneSource.Source
import scala.utility.SugarBowl.RichMappable

class EnvironmentTest extends AnyFunSuite {

  test("Apply accepting width should return an environment with an equal number of cols") {
    assert(Environment(5, 10).map.cols == 5)
  }

  test("'width' method should be equals to the matrix cols number") {
    assert(Environment(5, 10).width == 5)
  }

  test("Apply accepting height should return an environment with an equal number of rows") {
    assert(Environment(5, 10).map.cols == 5)
  }

  test("'height' method should be equals to the matrix rows number") {
    assert(Environment(10, 5).height == 5)
  }

  test("'cells' methods should return a sequence of cells of the correct number") {
    assert(Environment(5, 10).cells.size == 5 * 10)
  }

  test("'cells' methods should return a sequence of 'default' cells") {
    assert(Environment(5, 10).cells.forall(Cell.equals(_, Cell())))
  }

  test("'cells' methods should return a sequence of the specified cell") {
    Cell(TemperatureProperty.maxValue, HumidityProperty.maxValue, PressureProperty.maxValue) ~>
      (cell => assert(Environment(5, 10, cell).cells.forall(Cell.equals(_, cell))))
  }
}
