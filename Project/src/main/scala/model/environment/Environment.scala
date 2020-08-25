package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Zone.{border, in}
import scala.model.environment.matrix.Size
import scala.model.environment.property.Property
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.source.{ContinuousSource, InstantaneousSource, PropertySource, SeasonalSource, ZoneSource}
import scala.model.environment.utility.IteratorHelper._
import scala.model.environment.property.source.InstantaneousSource.indexed

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

  def apply(environment: Environment, source: PropertySource[Property]): Environment = source match {
    case source: InstantaneousSource[Property] => applyFilterX(environment, indexed(source, environment.map.cols,
      environment.map.rows))
    case source: ContinuousSource[Property] => ContinuousSource.instantaneous(source) match {
      case None => environment
      case Some(value) => applyFilterX(environment, indexed(value, environment.map.cols,
        environment.map.rows))//applyFilter(environment, value)
    }
    case propertySource: SeasonalSource[Property] => applySeason(environment, propertySource)
  }

  def apply(environment: Environment, variations: Iterable[(Int, Seq[GenericVariation[Property]])]): Environment = {
    variations.foreach(p => p._2.foreach(v => environment.map.data(p._1) += v)); environment
  }

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter(environment: Environment, source: ZoneSource[Property]): Environment =
    Environment(environment.map.mapPairs((pointXY, cell) => pointXY match {
      case p if in(p._1, p._2, source) => cell + source.filter(p._1 - border(source)(Size.Left),
        p._2 - border(source)(Size.Top))
      case _ => cell
    }))

  private def applyFilterX(environment: Environment, source: Iterable[(Int, GenericVariation[Property])])
  : Environment = { Environment(DenseMatrix.create(environment.map.rows, environment.map.cols, environment.map.data
      .zipWithIndex.map(p => source.iterator.conditionalNext(_._1 == p._2) match {
      case Some(variation) => p._1 + variation._2
      case _ => p._1
    })))
  }

  /**
   * Apply a seasonal variation to an environment
   *
   * @param environment to which apply the filter
   * @param variator to apply
   * @return an environment to which is applied the filter
   */
  def applySeason(environment: Environment, variator: SeasonalSource[Property]): Environment = {
    val variation = GenericVariation(SeasonalSource.nextValue(variator))
    Environment(environment.map.map(_ + variation))
    //Environment(environment.map.parallelMap(_ + variation)(Cell()))
  }
}