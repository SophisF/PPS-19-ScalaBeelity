package scala.model.property

import scala.model.matrix.Point
import scala.model.property.Property.Property

trait PropertyVariation {

  def property: Property
  def value: Int
}
object PropertyVariation {

  case class Variation(property: Property, value: Int) extends PropertyVariation
  case class PointVariation(property: Property, value: Int, x: Int, y: Int) extends PropertyVariation with Point
}