package scala.model.environment.property

import org.scalatest.funsuite.AnyFunSuite

/**
 * Test property trait
 *
 * @author Paolo Baldini
 */
class PropertyTypeTest extends AnyFunSuite {
  /*
  private object Props extends Property {
    type ValueType = Int

    def default = 0

    case class State_(state: Int) extends State

    case class Var(operation: State => State) extends Variation

  }

  test("Property state must have values of type 'ValueType'") {
    assertCompiles("Props.State_(5)")
  }

  test("Property state mustn't have values of type different from 'ValueType'") {
    assertDoesNotCompile("""Props.State_("string")""")
  }

  test("Property variation must accept a function who take a state and return a state") {
    assertCompiles("Props.Var(s => s)")
  }
*/
}
/*
  private case class NonComparableClass(x: Int, y: Int)
  private object NonComparablePropertyClass extends Property with Range {
    type ValueType = NonComparableClass
    var minValue: NonComparableClass = NonComparableClass(0, 0)
    var maxValue: NonComparableClass = NonComparableClass(0, 0)
    var default: NonComparableClass = NonComparableClass(0, 0)

    case class State(state: NonComparableClass) extends State
  }

  test("Limit function should return the passed value if it respects the limits") {
    val property = PropertyClass(0, 100, 0)
    assert(Range.limit(property)(50) == 50)
  }

  test("Limit function should return maxValue if it goes over the limits") {
    val property = PropertyClass(0, 100, 0)
    assert(Range.limit(property)(999) == 100)
  }

  test("Limit function should return maxValue if it goes under the limits") {
    val property = PropertyClass(0, 100, 0)
    assert(Range.limit(property)(-999) == 0)
  }

  test("Limit function should (try to) return values coherent also with explicit ordering operation") {
    val property = PropertyClass(0, 100, 0)
    // in case of equality, limit function return the max/min value. Obviously, an ad-hoc compare operation can be
    // implemented to break the rules. e.g.:
    // (_, y) => {
    //      if (y == property.minValue) 1
    //      else if (y == property.maxValue) -1
    //      else 0
    // }
    assert(Range.limit(property)(-999)((_, _) => 0) == property.maxValue)
  }

  test("Limit without an implicit or explicit 'Ordering' should not be callable") {
    NonComparablePropertyClass.minValue = NonComparableClass(1, 2)
    NonComparablePropertyClass.maxValue = NonComparableClass(3, 4)
    NonComparablePropertyClass.default = NonComparableClass(5, 6)

    assertDoesNotCompile("""NonComparablePropertyClass.PropertyState.limit(NonComparableClass(7, 8))""")
  }

  test("Limit with an explicit 'Ordering' should be callable") {
    NonComparablePropertyClass.minValue = NonComparableClass(1, 2)
    NonComparablePropertyClass.maxValue = NonComparableClass(3, 4)
    NonComparablePropertyClass.default = NonComparableClass(5, 6)

    assert(Range.limit(NonComparablePropertyClass)(NonComparableClass(7, 8))((_, _) => 0)
      == NonComparablePropertyClass.maxValue)
  }

  test("Vary shouldn't have strange behaviour (here, the limit function is bypassed)") {
    NonComparablePropertyClass.minValue = NonComparableClass(1, 2)
    NonComparablePropertyClass.maxValue = NonComparableClass(3, 4)
    NonComparablePropertyClass.default = NonComparableClass(5, 6)

    val op: (NonComparableClass, NonComparableClass) => NonComparableClass = (_, _) => NonComparableClass(-1, -1)
    assert(Property.PropertyState.vary(NonComparablePropertyClass.State(NonComparableClass(1, 1)), NonComparableClass(0, 0))(op, (_, y) =>
      { if (y==NonComparablePropertyClass.minValue) 1; else if (y==NonComparablePropertyClass.maxValue) -1; else 0 })
      .state == NonComparableClass(-1, -1))
  }
}*/
