package scala.model.environment

import scala.model.environment.property.Property
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.time.Timed.isEnded

/**
 * Manager of the environment, that control its evolution.
 *
 * @param environment    , the environment to manage.
 * @param propertySources , the property to at the environment.
 */
case class EnvironmentManager(environment: Environment, propertySources: PropertySource[Property]*)

object EnvironmentManager {

  /**
   * Apply function.
   *
   * @param width  of environment.
   * @param height of environment.
   * @return an environment manager.
   */
  def apply[T <: Property](width: Int, height: Int): EnvironmentManager = EnvironmentManager(Environment(width, height))

  /**
   * Apply property source at the environment and control property source.
   *
   * @param manager , environment manager to evolute.
   * @return environment manager evoluted.
   *
   */
  def evolution(manager: EnvironmentManager): EnvironmentManager = EnvironmentManager(
      manager.propertySources.foldLeft(manager.environment)(Environment.apply),
      manager.propertySources.filter {
        case p: ContinuousSource[_] => !isEnded(p)
        case p => p.isInstanceOf[SeasonalSource[_]]
      } :_*
    )

  def addSource[T <: Property](manager: EnvironmentManager, source: PropertySource[T]): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySources :+ source.asInstanceOf[PropertySource[Property]]:_*)
}
