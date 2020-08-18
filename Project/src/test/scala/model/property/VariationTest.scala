package scala.model.property
/*
import org.scalatest.funsuite.AnyFunSuite

import scala.model.matrix.Point
import scala.model.property.realization.Temperature._
import scala.model.property.realization.Temperature

class VariationTest extends AnyFunSuite {

  test("GenericVariation could be used to vary a property state") {
    val variation = Temperature.TemperatureVariation.incrementVariation(Temperature.minValue)
    assert(variation.operation(TemperatureState(5)).state == 5 + Temperature.minValue)
  }

  test("GenericVariation could be used to vary a property state if type is different from property one") {
    val variation = Variation(new Property {
      type ValueType = String

      def default: ValueType = ""
    }, "")
    assertDoesNotCompile("TemperatureState(5).vary(variation.value).state")
  }

  test("PointVariation is related to a Point and thus is sortable") {
    val variation1 = PointVariation("", 1, 2)
    val variation2 = PointVariation("", 1, 3)
    assert(Point.compare(variation1, variation2) == -1)
  }
}
*/