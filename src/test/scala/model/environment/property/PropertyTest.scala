package scala.model.environment.property

import breeze.linalg.DenseMatrix
import org.scalatest.funsuite.AnyFunSuite

class PropertyTest extends AnyFunSuite {

  test("I can create a state of property A and assign it to an Property#State var/val") {
    assertCompiles("val a: Property#State = A.AState(5)")
  }

  test("I can create a variation of property A and assign it to an Property#Variation var/val") {
    assertCompiles("val a: Property#Variation = A.AVariation(5)")
  }

  test("I can create a state of property A and assign it to an A.StateType var/val") {
    assertCompiles("val a: A.StateType = A.AState(5)")
  }

  test("I can create a state of property A and assign it to an A.VariationType var/val") {
    assertCompiles("val a: A.VariationType = A.AVariation(5)")
  }

  test("I cannot assign a state of property B to an A.StateType var/val") {
    assertDoesNotCompile("val a: A.StateType = B.BState(5)")
  }

  test("I cannot assign a variation of property B to an A.VariationType var/val") {
    assertDoesNotCompile("val a: A.VariationType = B.BVariation(5)")
  }

  test("I can assign a the result of a variation of A to a Property#State") {
    assertCompiles("val a: Property#State = A.AVariation(5).vary(A.AState(5))")
  }

  test("I can assign a the result of a variation of A to a State of A") {
    assertCompiles("val a: A.StateType = A.AVariation(5).vary(A.AState(5))")
  }

  object A extends Property {
    override type ValueType = Int
    override type StateType = AState
    override type VariationType = AVariation
    case class AState(value: Int) extends State { override def numericRepresentation(percentage: Boolean): Int = value }
    case class AVariation(value: Int) extends Variation {
      override def isNull: Boolean = value == 0
      override def vary[S <: StateType](state: S): AState = AState(state.value + value)
    }
    override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] = ???
  }

  object B extends Property {
    override type ValueType = Int
    override type StateType = BState
    override type VariationType = BVariation
    case class BState(value: Int) extends State { override def numericRepresentation(percentage: Boolean): Int = value }
    case class BVariation(value: Int) extends Variation {
      override def isNull: Boolean = value == 0
      override def vary[S <: StateType](state: S): BState = BState(state.value + value)
    }
    override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] = ???
  }
}
