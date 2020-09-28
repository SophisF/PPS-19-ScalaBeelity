package scala.model.environment.property.typed.int

import org.scalatest.funsuite.AnyFunSuite


class IntPropertyTest extends AnyFunSuite{

  val intProperty: IntProperty = new IntProperty {
    override def default: Int = 5

    override def minValue: Int = 0

    override def maxValue: Int = 10
  }

  test("IntPropertyTest should give the correct default value"){
    assert(intProperty.default == 5)
  }

  test("IntRange should give a correct center of the range") {
    assert(intProperty.rangeCenter equals 5)
  }

  test("IntRange should give the max value of the range") {
    assert(intProperty.maxValue equals 10)
  }

  test("IntRange should give the min value of the range") {
    assert(intProperty.minValue equals 0)
  }

  test("IntRange should give correct width of the range") {
    assert(intProperty.rangeWidth equals 10)
  }

  test("IntRange should give a correct range centered of 0") {
    assert(intProperty.zeroCenteredRange equals(-5, 5))
  }

}
