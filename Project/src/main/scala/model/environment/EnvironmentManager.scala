package scala.model.environment

import scala.model.environment.property.Property
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.realization.TemperaturePropertyHelper.toState
import scala.model.environment.property.realization.HumidityPropertyHelper.toState
import scala.model.environment.property.realization.PressurePropertyHelper.toState
import scala.model.environment.property.source.ZonePropertySource.{ContinuousZonePropertySource, InstantaneousZonePropertySource}
import scala.model.environment.property.source.{PropertySource, SeasonalPropertySource}
import scala.model.environment.time.Timed.isEnded

object EnvironmentManager {

  /**
   * 1- con granularità temporale maggiore della durata del filtro posso pensare di usare un filtro istantaneo
   * 2- potrei salvarmi un parametro lastupdate per vedere se l'ultimo aggiornamento è stato fatto
   */

  /**
   * Manager of the environment, that control its evolution.
   *
   * @param environment    , the environment to manage.
   * @param propertySource , the property to at the environment.
   */
  case class EnvironmentManager(environment: Environment, propertySource: PropertySource[Property]*)

  /**
   * Apply function.
   *
   * @param width  of environment.
   * @param height of environment.
   * @return an environment manager.
   */
  def apply[T <: Property](width: Int, height: Int): EnvironmentManager = EnvironmentManager(
    Environment(width, height, Cell(TemperatureProperty.default, HumidityProperty.default, PressureProperty.default)))

  /**
   * Apply property source at the environment and control property source.
   *
   * @param manager , environment manager to evolute.
   * @return environment manager evoluted.
   *
   */
  def evolution(manager: EnvironmentManager): EnvironmentManager =
    EnvironmentManager(
      manager.propertySource.foldLeft(manager.environment)((x, y) => Environment.apply(x, y)),
      manager.propertySource.filter {
        case _: SeasonalPropertySource[_] => true
        case _: InstantaneousZonePropertySource[_] => false
        case p: ContinuousZonePropertySource[_] => !isEnded(p)
      }: _*
    )

  def addSource[T <: Property](manager: EnvironmentManager, propertySource: PropertySource[T]): EnvironmentManager
  = EnvironmentManager(manager.environment, manager.propertySource.appended(propertySource.asInstanceOf[PropertySource[Property]]): _*)
}
