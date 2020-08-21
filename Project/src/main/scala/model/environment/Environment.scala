package scala.model.environment

import scala.collection.parallel.CollectionConverters._

import breeze.generic.UFunc
import breeze.linalg.DenseMatrix
import breeze.linalg.operators.OpAdd

import scala.model.environment.matrix.Zone.{border, in}
import scala.model.environment.matrix.Size
import scala.model.environment.property.Property
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.source.{ContinuousSource, InstantaneousSource, PropertySource, SeasonalSource, ZoneSource}

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

  def apply[T <: Property](environment: Environment, source: PropertySource[T]): Environment = source match {
    case source: InstantaneousSource[T] => applyFilter(environment, source)
    case source: ContinuousSource[T] => ContinuousSource.instantaneous(source) match {
      case None => environment
      case Some(value) => applyFilter(environment, value)
    }
    case propertySource: SeasonalSource[T] => applySeason(environment, propertySource)
  }

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter[T <: Property](environment: Environment, source: ZoneSource[T]): Environment =
    Environment(environment.map.mapPairs((pointXY, cell) => pointXY match {
      case p if in(p._1, p._2, source) => cell + source.filter(p._1 - border(source)(Size.Left),
        p._2 - border(source)(Size.Top))
      case _ => cell
    }))

  /**
   * Apply a seasonal variation to an environment
   *
   * @param environment to which apply the filter
   * @param variator to apply
   * @return an environment to which is applied the filter
   */
  def applySeason[T <: Property](environment: Environment, variator: SeasonalSource[T]): Environment = {
    val variation = GenericVariation(SeasonalSource.nextValue(variator))
    //Environment(environment.map.map(_ + variation))
    Environment(DenseMatrix.create(environment.map.rows, environment.map.cols,
      environment.map.data.par.map(_ + variation).toArray))
  }
}