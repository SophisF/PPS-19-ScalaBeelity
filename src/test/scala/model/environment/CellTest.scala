package scala.model.environment

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.PropertyType
import scala.model.environment.property.PropertyType.{Humidity, Pressure, Temperature}
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}

/**
 * Test for cell entities
 */
class CellTest extends AnyFunSuite {
  private val defaultTemp = TemperatureProperty.default
  private val minTemp = TemperatureProperty.minValue
  private val maxTemp = TemperatureProperty.maxValue
  private val variationTemp: Int => TemperatureProperty#VariationType = TemperatureProperty.variation
  private val defaultHum = HumidityProperty.default
  private val minHum = HumidityProperty.minValue
  private val maxHum = HumidityProperty.maxValue
  private val variationHum: Int => HumidityProperty#VariationType = HumidityProperty.variation
  private val defaultPres = PressureProperty.default
  private val minPres = PressureProperty.minValue
  private val maxPres = PressureProperty.maxValue
  private val variationPres: Int => PressureProperty#VariationType = PressureProperty.variation

  test("Get `Property.Temperature` should return correct value") {
    assert(Cell(defaultTemp + 1)(Temperature).numericRepresentation(false) == defaultTemp + 1)
  }

  test("Get `Property.Humidity` should return correct value") {
    assert(Cell(0, defaultHum + 1, 0)(Humidity).numericRepresentation(false) == defaultHum + 1)
  }

  test("Get `Property.Pressure` should return correct value") {
    assert(Cell(0, 0, defaultPres + 1)(Pressure).numericRepresentation(false) == defaultPres + 1)
  }

  test("A target property should change according to variation") {
    val newCell = Cell(defaultTemp, defaultHum, defaultPres) + variationHum(1)
    assert(Cell.equals(newCell, Cell(defaultTemp, defaultHum +1, defaultPres)))
  }

  test("Non-target properties should not change after a variation") {
    val cell = Cell(defaultTemp, defaultHum, defaultPres)
    val newCell = cell + variationHum(1)
    assert(PropertyType.propertiesType.filterNot(_ == Humidity).foldLeft(true)((bool, prop) =>
      cell(prop).numericRepresentation(false) == newCell(prop).numericRepresentation(false) && bool))
  }

  test("Sum should never exceed max property value") {
    assert(Cell.equals(Cell(maxTemp, maxHum, maxPres), Cell(maxTemp, maxHum, maxPres) + variationTemp(1)))
  }

  test("Sum should never exceed min property value") {
    assert(Cell.equals(Cell(minTemp, minHum, minPres), Cell(minTemp, minHum, minPres) + variationTemp(-1)))
  }

  test("Sum operation with `Optional None` should return the cell unmodified") {
    assert(Cell.equals(Cell(), Cell() +? Option.empty))
  }

  test("Sum operation with `Optional Some` should work as normal sum") {
    assert(Cell.equals(Cell(defaultTemp +1, defaultHum +2, defaultPres +3), Cell(defaultTemp, defaultHum, defaultPres)
      +? Option(variationTemp(1)) +? Option(variationHum(2)) +? Option(variationPres(3))))
  }

  test("Two cell with same elements should be considered equals") {
    assert(Cell.equals(Cell(), Cell()))
  }

  test("Two cell with different elements should not be considered equals") {
    assert(!Cell.equals(Cell(), Cell(Int.MinValue)))
  }
}