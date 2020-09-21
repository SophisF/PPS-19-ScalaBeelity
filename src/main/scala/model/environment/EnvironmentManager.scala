package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.adapter.Cell.toCell
import scala.model.environment.ClimateManager.{generateLocalChanges, generateSeason, randomInstantaneousFilter}
import scala.model.environment.matrix.Size
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.properties
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.time.Timed.isEnded

/**
 * Manager of the environment, that control its evolution.
 *
 * @param environment     , the environment to manage.
 * @param propertySources , the property to at the environment.
 */
case class EnvironmentManager(environment: Environment, propertySources: PropertySource[Property]*) {
  def cells(): DenseMatrix[scala.model.adapter.Cell] = environment.map.mapValues(toCell)
}

object EnvironmentManager {

  /**
   * Apply function.
   *
   * @param width  of environment.
   * @param height of environment.
   * @return an environment manager.
   */
  def apply[T <: Property](width: Int, height: Int): EnvironmentManager = {
    val seasonalEnvironment = generateSeason().foldLeft(EnvironmentManager(Environment(width, height)))(addSource)
    evolution(properties().foldLeft(seasonalEnvironment)((env, prop) => addSource(env, randomInstantaneousFilter(prop, Size(width, height)))))
  }


  /**
   * Apply property source at the environment and control property source.
   *
   * @param manager , environment manager to evolute.
   * @return environment manager evoluted.
   *
   */
  def evolution(manager: EnvironmentManager): EnvironmentManager =
    generateLocalChanges((manager.environment.map.cols, manager.environment.map.rows), 200)
      .foldLeft(EnvironmentManager(
        manager.propertySources.foldLeft(manager.environment)(Environment.apply),
        manager.propertySources.filter {
          case p: ContinuousSource[_] => !isEnded(p)
          case p => p.isInstanceOf[SeasonalSource[_]]
        }: _*
      ))(addSource)


  def addSource[T <: Property](manager: EnvironmentManager, source: PropertySource[T]): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySources :+ source.asInstanceOf[PropertySource[Property]]: _*)
}
