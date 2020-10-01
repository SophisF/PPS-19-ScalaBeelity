package scala.model.environment.property.realization.integer

import org.scalatest.funsuite.AnyFunSuite

/**
 * Test Property trait with value type Int
 * Further tests were made in superclass
 */
class PropertyTest extends AnyFunSuite{

  private val property: Property = new Property {
    override def default: Int = 5

    override def minValue: Int = 0

    override def maxValue: Int = 10
  }

  test("Property should give the correct default value"){
    assert(property.default == 5)
  }

  test("Property should give the correct min value"){
    assert(property.minValue == 0)
  }

  test("Property should give the correct max value"){
    assert(property.maxValue == 10)
  }

  test("Property should be of type property.Property with an integer.Range") {
    assert(property.isInstanceOf[scala.model.environment.property.Property with Range])
  }

  // FURTHER TESTS WERE MADE IN SUPERCLASS
}
