package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.Property
import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.source.ZonePropertySource.{ContinuousZonePropertySource, InstantaneousZonePropertySource, border, in}
import scala.model.environment.property.source.{PropertySource, SeasonalPropertySource, ZonePropertySource}
import scala.model.environment.time.FiniteData

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
   * @param size of the grid/map
   * @param defaultCell initial value for the cells of the map
   * @return the environment
   */
  def apply(width: Int, height: Int, defaultCell: Cell): Environment =
    new Environment(DenseMatrix.create(width, height, Iterator.continually(defaultCell).take(width * height).toArray))

  //TODO: Cercare un metodo per rimuovere i case match innestati.
  def apply[T <: Property](environment: Environment, propertySource: PropertySource[T]): Environment =
    propertySource match {
      case propertySource: InstantaneousZonePropertySource[T] => applyFilter(environment, propertySource)
      case propertySource: ContinuousZonePropertySource[T] =>
        FiniteData.dataAtInstant[DenseMatrix[GenericVariation[T]], ContinuousZonePropertySource[T]](propertySource)(ZonePropertySource.nextValue[T]) match {
          case None => environment
          case Some(value) => applyFilter(environment, InstantaneousZonePropertySource(value, propertySource.x,
            propertySource.y, propertySource.width, propertySource.height))
        }
      case propertySource: SeasonalPropertySource[T] => applySeason(environment, propertySource)
    }

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter[T <: Property](environment: Environment, filter: InstantaneousZonePropertySource[T]): Environment =
    Environment(environment.map.mapPairs((pointXY, cell) => pointXY match {
      case p if in(p._1, p._2, filter) =>
        cell + filter.data(p._1 - border(filter)(Size.Left), p._2 - border(filter)(Size.Top))
      case _ => cell
    }))

  /**
   * Apply a seasonal variation to an environment
   *
   * @param environment to which apply the filter
   * @param variator to apply
   * @return an environment to which is applied the filter
   */
  def applySeason[T <: Property](environment: Environment, variator: SeasonalPropertySource[T]): Environment = {
    val variation = GenericVariation(SeasonalPropertySource.nextValue(variator))
    Environment(environment.map.map(_ + variation))
  }
}