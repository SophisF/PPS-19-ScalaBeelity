package scala.model.environment

import breeze.linalg.DenseMatrix.create
import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time
import scala.model.Time.now
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.source.ContinuousSource
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.ZoneSource.Source
import scala.utility.SugarBowl.RichMappable

/**
 * Tests environment
 */
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

  test("The result of the application of an instantaneous filter should be different from the initial env") {
    val filter = Source[TemperatureProperty](create(3, 3, Array(1,2,3,4,5,6,7,8,9)), 1, 1)
    assert(!Environment(Environment(5,5), filter).cells.iterator.sameElements(Environment(5,5).cells.iterator))
  }

  test("The result of the application of an instantaneous filter should be predictable") {
    val modifiedEnvironment = Source[TemperatureProperty](create(3, 3, Array(1,2,3,4,5,6,7,8,9)), 0, 0) ~>
      (filter => Environment(Environment(5,5), filter))

    val default = TemperatureProperty.default
    assert(modifiedEnvironment.cells.map(_.temperature.numericRepresentation(false)).iterator.sameElements(
      Array(
        default + 5,  default + 8,  default, default, default,
        default + 6,  default + 9,  default, default, default,
        default,      default,      default, default, default,
        default,      default,      default, default, default,
        default,      default,      default, default, default,
      )
    ))
  }

  test("The variation of the application of an continuous filter without increment the time should be nul") {
    val filter = create(3, 3, Array[TemperatureProperty#TimedVariation](1,2,3,4,5,6,7,8,9))
    val source = new ContinuousSource[TemperatureProperty](0, 0, 100)(filter)
    val modifiedEnvironment = Environment(Environment(5,5), source)
    assert(
      modifiedEnvironment.cells.map(_.temperature.numericRepresentation(false)).iterator
        .sameElements(
      Environment(5,5).cells.map(_.temperature.numericRepresentation(false)).iterator)
    )
  }

  test("There should be a variation after the application of an continuous filter with time increment") {
    val filter = create(3, 3, Array[TemperatureProperty#TimedVariation](1,2,3,4,5,6,7,8,9))
    val source = new ContinuousSource[TemperatureProperty](0, 0, 100)(filter)
    Time increment 200
    val modifiedEnvironment = Environment(Environment(5,5), source)
    assert(
      !modifiedEnvironment.cells.map(_.temperature.numericRepresentation(false)).iterator
        .sameElements(
      Environment(5,5).cells.map(_.temperature.numericRepresentation(false)).iterator)
    )
  }

  test("There should be a partial variation with time increment without reach the duration") {
    val filter = create(3, 3, Array[TemperatureProperty#TimedVariation](1,2,3,4,5,6,7,8,9))
    val source = new ContinuousSource[TemperatureProperty](0, 0, 100)(filter)
    Time increment 50
    val modifiedEnvironment = Environment(Environment(5,5), source)
    val default = TemperatureProperty.default
    assert(modifiedEnvironment.cells.map(_.temperature.numericRepresentation(false)).iterator.sameElements(
      Array(
        default + 2,  default + 4,  default, default, default,
        default + 3,  default + 4,  default, default, default,
        default,      default,      default, default, default,
        default,      default,      default, default, default,
        default,      default,      default, default, default,
      )
    ))
  }

  test("Seasonal variation application should return the same value after a year") {
    val modifiedEnvironment = Environment(Environment(5,5), SeasonalSource(TemperatureProperty.seasonalTrend))
    Time increment 365
    assert(modifiedEnvironment.cells.map(_.temperature.numericRepresentation()).iterator.sameElements(
      Environment(Environment(5,5), SeasonalSource(TemperatureProperty.seasonalTrend)).cells
        .map(_.temperature.numericRepresentation()).iterator
    ))
  }

  test("Seasonal variation application should return the different values into the year") {
    val modifiedEnvironment = Environment(Environment(5,5), SeasonalSource(TemperatureProperty.seasonalTrend))
    Time increment 60
    assert(!modifiedEnvironment.cells.map(_.temperature.numericRepresentation()).iterator.sameElements(
      Environment(Environment(5,5), SeasonalSource(TemperatureProperty.seasonalTrend)).cells
        .map(_.temperature.numericRepresentation()).iterator
    ))
  }

  private implicit def timedVariation(value: Int): TemperatureProperty.TimedVariation =
    TemperatureProperty.timedVariation(value, now(), 100)
}
