package scala.model

import scala.model.property.Property.{Humidity, Pressure, Temperature, range}
import scala.model.property.PropertySource.{ContinuousPropertySource, InstantaneousPropertySource}
import scala.model.time.EvolvableData.inference
import scala.model.time.Timed.ended

object EnvironmentManager {

  /**
   * 1- con granularità temporale maggiore della durata del filtro posso pensare di usare un filtro istantaneo
   * 2- potrei salvarmi un parametro lastupdate per vedere se l'ultimo aggiornamento è stato fatto
   */

  case class EnvironmentManager(environment: Environment, continuousFilters: Array[ContinuousPropertySource])

  def apply(width: Int, height: Int): EnvironmentManager = {
    EnvironmentManager(Environment((width, height), Cell(range(Temperature).default, range(Humidity).default,
      range(Pressure).default)), Array())
  }

  def evolution(manager: EnvironmentManager): EnvironmentManager = {
    EnvironmentManager(
      manager.continuousFilters.map(it => inference(it) match {
        case filter if filter.nonEmpty =>
          Option.apply(InstantaneousPropertySource(filter.get, it.x, it.y, it.width, it.height))
        case _ => Option.empty
      }).filter(_.nonEmpty).map(_.get).foldLeft(manager.environment)((environment, filter) => environment + filter),
      manager.continuousFilters.filter(ended(_))
    )
  }

  def addSource(manager: EnvironmentManager, continuousPropertySource: ContinuousPropertySource): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.continuousFilters.appended(continuousPropertySource))
}
