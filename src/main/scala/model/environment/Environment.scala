package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Zone.{border, in}
import scala.model.environment.matrix.Size.Border._
import scala.model.environment.property.{Property, TimedProperty}
import scala.model.environment.property.source.{PropertySource, SeasonalSource, ZoneSource}

/**
 * A first scratch of the environment class.
 *
 * @param map of the environment. Represented as a grid.
 *
 * @author Paolo Baldini
 */
case class Environment private (map: DenseMatrix[Cell])

object Environment {

  /**
   * Create a new environment
   *
   * @param width of the grid/map
   * @param height of the grid/map
   * @param defaultCell initial value for the cells of the map
   * @return the environment
   */
  def apply(width: Int, height: Int, defaultCell: Cell = Cell()): Environment = Environment(
    DenseMatrix.create(width, height, Iterator continually defaultCell take(width * height) toArray))

  def apply(environment: Environment, source: PropertySource[_]): Environment = source match {
    case source: ZoneSource[Property] => applyFilter(environment, source)
    case source: SeasonalSource[TimedProperty] => applySeason(environment, source)
    case _ => environment
  }

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter(environment: Environment, source: ZoneSource[Property]): Environment = {
    val filter = source.filter
    Environment(environment.map.mapPairs {
      case ((x, y), cell) if in(x, y, source) => cell + filter(y - border(source)(Top), x - border(source)(Left))
      case (_, cell) => cell
    })
  }

  /**
   * Apply a seasonal variation to an environment
   *
   * @param environment to which apply the filter
   * @param variator to apply
   * @return an environment to which is applied the filter
   */
  def applySeason(environment: Environment, source: SeasonalSource[TimedProperty]): Environment =
    source.nextValue() match {
      case variation if variation isNull => environment
      case variation => Environment(environment.map.map(_ + variation))
    }
}