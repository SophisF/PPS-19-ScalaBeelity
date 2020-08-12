package scala.model

import breeze.linalg.DenseMatrix

import scala.model.matrix._
import scala.model.property.Property.Property
import scala.model.property.{Property, PropertySource}
import scala.model.property.PropertySource.SeasonalPropertySource
import scala.model.property.ZonePropertySource.{ContinuousZonePropertySource, InstantaneousZonePropertySource, border, in}
import scala.model.time.{EvolvableData, PeriodicalData}

/**
 * A first scratch of the environment class.
 *
 * @param map of the environment. Represented as a grid.
 *
 * @author Paolo Baldini
 */
case class Environment private (map: DenseMatrix[Cell])

object Environment {
  type Matrix[T] = Array[Array[T]]
  type SizeWH = (Int, Int)

  /**
   * Create a new environment
   *
   * @param size of the grid/map
   * @param defaultCell initial value for the cells of the map
   * @return the environment
   */
  def apply(size: SizeWH, defaultCell: Cell) = new Environment(DenseMatrix.create(size._1, size._2, Iterator
    .continually(defaultCell).take(size._1 * size._2).toArray))

  def apply(environment: Environment, propertySource: PropertySource): Environment = propertySource match {
    case propertySource: InstantaneousZonePropertySource =>
      applyFilter(environment, propertySource.asInstanceOf[InstantaneousZonePropertySource])
    case propertySource: ContinuousZonePropertySource => EvolvableData.dataAtInstant(propertySource) match {
      case None => environment
      case Some(value) => applyFilter(environment, InstantaneousZonePropertySource(value, propertySource.x,
        propertySource.y, propertySource.width, propertySource.height))
    }
    case propertySource: SeasonalPropertySource =>
      applySeason(environment, propertySource.asInstanceOf[SeasonalPropertySource])
  }

  /**
   * Apply a filter to an environment
   *
   * @param environment to which apply the filter
   * @param filter to apply
   * @return an environment to which is applied the filter
   */
  def applyFilter(environment: Environment, filter: InstantaneousZonePropertySource): Environment =
    Environment(environment.map.mapPairs((pointXY, cell) => pointXY match {
      case p if in(p._1, p._2, filter) =>
        cell + filter.data(p._1 - border(filter)(Size.Left), p._2 - border(filter)(Size.Top))
      case _ => cell
    }))

  def applySeason(environment: Environment, variator: SeasonalPropertySource): Environment = {
    val scalar: (Property, Int) = (variator.property, PeriodicalData.dataAtInstant(variator))

    Environment(environment.map.map(cell => scalar._1 match {
      case Property.Temperature => Cell(cell.temperature + scalar._2, cell.humidity, cell.pressure)
      case Property.Humidity => Cell(cell.temperature, cell.humidity + scalar._2, cell.pressure)
      case Property.Pressure => Cell(cell.temperature, cell.humidity, cell.pressure + scalar._2)
    }))
  }
}