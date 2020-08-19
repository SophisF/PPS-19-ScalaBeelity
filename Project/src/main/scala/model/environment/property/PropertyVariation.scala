package scala.model.environment.property

import scala.model.environment.matrix.Point
import scala.model.environment.property.Property.Property

trait PropertyVariation {

  def property: Property
  def value: Int
}
object PropertyVariation {

  case class Variation(property: Property, value: Int) extends PropertyVariation
  case class PointVariation(property: Property, value: Int, x: Int, y: Int) extends PropertyVariation with Point
}