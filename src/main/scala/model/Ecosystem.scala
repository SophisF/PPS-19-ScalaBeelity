package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.bee.{Colony, Queen}
import scala.model.bees.genotype.Genotype
import scala.model.environment.EnvironmentManager.evolution
import scala.model.environment.{Cell, Environment, EnvironmentManager}
import scala.util.Random.{nextInt => randomUntil}
import scala.utility.Point
import scala.utility.SugarBowl.RichMappable

case class Ecosystem(environmentManager: EnvironmentManager, colonies: Colony*)

object Ecosystem {

  def apply(coloniesCount: Int, width: Int, height: Int): Ecosystem = {
    EnvironmentManager(width, height) ~> (
      manager => Ecosystem(manager, (1 to coloniesCount).map(_ => createColony(manager.environment)()) :_*))
  }

  def update(ecosystem: Ecosystem): Ecosystem = Ecosystem(evolution(ecosystem environmentManager),
      Colony.manage(ecosystem.colonies.flatMap(_.update(Time incrementValue)(ecosystem environmentManager))
        .filter(_ isColonyAlive) toList).filter(_ isColonyAlive) :_*)

  def createColony(
    environment: Environment
  )(
    position: Point = (randomUntil(environment width), randomUntil(environment height))
  ): Colony = {
    val genotype = Genotype()
    val cell: Cell = environment.map(position.x, position.y)
    val queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0, position, this.createColony(environment),
      cell.temperature.numericRepresentation(false), cell.pressure.numericRepresentation(false),
      cell.humidity.numericRepresentation(false))
    queen.colony
  }
}
