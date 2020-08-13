package scala.model

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Cell.operation
import scala.model.property.Property.{Humidity, Pressure, Temperature, range}
import scala.model.property.PropertyVariation.Variation

/**
 * Test for cell entities
 *
 * @author Paolo Baldini
 */
class CellTest extends AnyFunSuite {

  private val minTemp = range(Temperature).minValue
  private val maxTemp = range(Temperature).maxValue
  private val minHum = range(Humidity).minValue
  private val maxHum = range(Humidity).maxValue
  private val minPres = range(Pressure).minValue
  private val maxPres = range(Pressure).maxValue

  test("Get `Property.Temperature` should return correct value") {
    assert(2 == Cell(2, 0, 0).get(Temperature))
  }

  test("Get `Property.Humidity` should return correct value") {
    assert(3 == Cell(0, 3, 0).get(Humidity))
  }

  test("Get `Property.Pressure` should return correct value") {
    assert(4 == Cell(0, 0, 4).get(Pressure))
  }

  test("Sum operation is field-wise") {
    assert(Cell(2, 3, 4) == Cell(1, 1, 1) + Variation(Temperature, 1) + Variation(Humidity, 2) + Variation(Pressure, 3))
  }

  test("Sum should never exceed max property value") {
    assert(Cell(maxTemp, maxHum, maxPres) == Cell(1, 1, 1) + Variation(Temperature, maxTemp) +
      Variation(Humidity, maxHum) + Variation(Pressure, maxPres))
  }

  test("Sum should never exceed min property value") {
    assert(Cell(minTemp, minHum, minPres) == Cell(0, 0, 0) + Variation(Temperature, minTemp -1) +
      Variation(Humidity, minHum -1) + Variation(Pressure, minPres -1))
  }

  test("Sum operation with `Optional None` should return the cell unmodified") {
    assert(Cell(1, 2, 3) == Cell(1, 2, 3) + Option.empty)
  }

  test("Sum operation with `Optional Some` should work as normal sum") {
    assert(Cell(2, 3, 4) == Cell(1, 1, 1) + Option.apply(Variation(Temperature, 1)) +
      Option.apply(Variation(Humidity, 2)) + Option.apply(Variation(Pressure, 3)))
  }

  test("Two cell with same elements should be considered equals") {
    assert(Cell(1, 2, 3) == Cell(1, 2, 3))
  }

  test("Two cell with different elements should not be considered equals") {
    assert(Cell(1, 2, 3) != Cell(-1, -2, -3))
  }

  test("Function 'operation' apply a mathematical operations between fields of the two cell") {
    assert(Cell(6, 4, 2) == operation(Cell(3, 2, 1), Cell(3, 2, 1))((_1, _2) => _1 + _2))
    assert(Cell(0, 0, 0) == operation(Cell(3, 2, 1), Cell(3, 2, 1))((_1, _2) => _1 - _2))
    assert(Cell(9, 4, 1) == operation(Cell(3, 2, 1), Cell(3, 2, 1))((_1, _2) => _1 * _2))
    assert(Cell(1, 1, 1) == operation(Cell(2, 2, 2), Cell(2, 2, 2))((_1, _2) => _1 / _2))
  }

  test("Function 'operation' between properties should respect limits/range") {
    assert(Cell(maxTemp, maxHum, maxPres) == operation(Cell(maxTemp, maxHum, maxPres), Cell(1, 1, 1))((f, s) => f + s))
    assert(Cell(minTemp, minHum, minPres) == operation(Cell(minTemp, minHum, minPres), Cell(1, 1, 1))((f, s) => f - s))
    assert(Cell(maxTemp, maxHum, maxPres) == operation(Cell(maxTemp, maxHum, maxPres), Cell(2, 2, 2))((f, s) => f * s))
    // being the properties of type Int, division cannot exceed range
  }
}
