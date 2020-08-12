package scala.model

import scala.model.property.Property.{Humidity, Pressure, Temperature, range}
import scala.model.property.PropertySource.SeasonalPropertySource
import scala.model.property.PropertySource
import scala.model.property.ZonePropertySource.{ContinuousZonePropertySource, InstantaneousZonePropertySource}
import scala.model.time.Timed.isEnded

object EnvironmentManager {

  /**
   * 1- con granularità temporale maggiore della durata del filtro posso pensare di usare un filtro istantaneo
   * 2- potrei salvarmi un parametro lastupdate per vedere se l'ultimo aggiornamento è stato fatto
   */

  case class EnvironmentManager(environment: Environment, propertySource: Array[PropertySource])

  def apply(width: Int, height: Int): EnvironmentManager = {
    EnvironmentManager(Environment((width, height), Cell(range(Temperature).default, range(Humidity).default,
      range(Pressure).default)), Array())
  }

  def evolution(manager: EnvironmentManager): EnvironmentManager =
    EnvironmentManager(
      manager.propertySource.foldLeft(manager.environment)(Environment.apply),
      manager.propertySource.filter {
        case _: SeasonalPropertySource => true
        case _: InstantaneousZonePropertySource => false
        case p: ContinuousZonePropertySource => !isEnded(p)
      }
    )

  def addSource(manager: EnvironmentManager, propertySource: PropertySource): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySource.appended(propertySource))
}
