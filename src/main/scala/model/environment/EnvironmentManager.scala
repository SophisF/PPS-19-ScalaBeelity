package scala.model.environment

import model.environment.GeneratorClimateChange

import scala.model.environment.property.Property.{Humidity, Pressure, Temperature, range}
import scala.model.environment.property.PropertySource
import scala.model.environment.property.PropertySource.SeasonalPropertySource
import scala.model.environment.property.ZonePropertySource.{ContinuousZonePropertySource, InstantaneousZonePropertySource}
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
  case class EnvironmentManager(environment: Environment, propertySource: Array[PropertySource])

  def empty(): EnvironmentManager = apply(0, 0)

  /**
   * Apply function.
   *
   * @param width  of environment.
   * @param height of environment.
   * @return an environment manager.
   */
  def apply(width: Int, height: Int): EnvironmentManager = {
    GeneratorClimateChange.generateSeason().foldLeft(
      EnvironmentManager(
        Environment((width, height), Cell(range(Temperature).default, range(Humidity).default, range(Pressure).default)),
        Array())
    )(addSource)
  }

  /**
   * Apply property source at the environment and control property source.
   *
   * @param manager , environment manager to evolute.
   * @return environment manager evoluted.
   *
   */
  def evolution(manager: EnvironmentManager): EnvironmentManager = {
    GeneratorClimateChange.generateClimate(manager.environment.map.cols, manager.environment.map.rows, 1000)
      .foldLeft(
        EnvironmentManager(
          manager.propertySource.foldLeft(manager.environment)(Environment.apply),
          manager.propertySource.filter {
            case _: SeasonalPropertySource => true
            case _: InstantaneousZonePropertySource => false
            case p: ContinuousZonePropertySource => !isEnded(p)
          }
        )
      )(addSource)


  }

  /**
   * Add property source to the environment manager.
   *
   * @param manager        , the environment manager.
   * @param propertySource , property source to add.
   * @return an enviroment manager with the property added.
   */
  def addSource(manager: EnvironmentManager, propertySource: PropertySource): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySource.appended(propertySource))
}
