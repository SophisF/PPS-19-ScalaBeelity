package scala.model.property

import org.scalatest.funsuite.AnyFunSuite

class TimePropertyTypeTest extends AnyFunSuite {
/*
  test("InstantValue function and method should return same results if the same Property is passed") {
    val property: TimeProperty {
      type ValueType = Int
      type StateType = { def state: ValueType }
    } = new TimeProperty {
      type ValueType = Int
      def instantValue: Int => StateType = value => new StateType { def state: ValueType = value * value }
      def default: ValueType = 0
    }
    assert(property.instantValue(5).state == TimeProperty.instantValue(property, 5).state)
  }*/
}
