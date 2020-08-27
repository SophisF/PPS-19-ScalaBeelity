package scala.model.environment

import scala.model.environment.property.Property
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}
import scala.model.environment.property.source.InstantaneousSource.indexed
import scala.model.environment.property.source.{ContinuousSource, InstantaneousSource, PropertySource, SeasonalSource, ZoneSource}
import scala.model.environment.property.source.ContinuousSource.instantaneous
import scala.model.environment.time.Timed.isEnded

object EnvironmentManager {

  /**
   * Manager of the environment, that control its evolution.
   *
   * @param environment    , the environment to manage.
   * @param propertySources , the property to at the environment.
   */
  case class EnvironmentManager(environment: Environment, propertySources: Seq[PropertySource[Property]] = Seq empty)

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
  def evolution(manager: EnvironmentManager): EnvironmentManager = {
    EnvironmentManager(
      Environment(
        manager.environment, manager.propertySources.filter(_.isInstanceOf[ZoneSource[_]]).map {
          case source: ContinuousSource[Property] => instantaneous(source)
          case source: InstantaneousSource[Property] => Option(source)
        }.filterNot(_ isEmpty).flatMap(o => indexed(o get, manager.environment.map.cols, manager.environment.map.rows))
          .groupMap(_ _1)(_ _2)),//manager.propertySources.foldLeft(manager.environment)((x, y) => Environment.apply(x, y)),
      manager.propertySources.filter {
        case _: SeasonalSource[_] => true
        case _: InstantaneousSource[_] => false
        case p: ContinuousSource[_] => !isEnded(p)
      }
    )
  }

  def addSource[T <: Property](manager: EnvironmentManager, source: PropertySource[T]): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySources
      .appended(source.asInstanceOf[PropertySource[Property]]))
}
