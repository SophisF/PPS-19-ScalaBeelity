package scala.model.environment.property.source

import breeze.linalg._

import scala.model.environment.property.Property
import scala.model.environment.property.Variation._

sealed trait InstantaneousSource[T <: Property] extends ZoneSource[T]

/**
 * Object for a basic filter that contains a matrix representing the gaussian 3d function.
 * It also store the center position (source) on which apply the filter.
 *
 * @author Paolo Baldini
 */
object InstantaneousSource {

  case class InstantaneousSourceImpl[T <: Property](
    filter: DenseMatrix[GenericVariation[T]],
    x: Int, y: Int,
    width: Int, height: Int
  ) extends InstantaneousSource[T]

  //def linearize[T <: Property](filter: InstantaneousSource[T]): Array[PointVariation[T]] =
  //  filter.filter.mapPairs((p, v) => PointVariation(v.value, p._1, p._2)).data
}