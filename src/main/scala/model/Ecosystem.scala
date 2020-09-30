package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.bee.{Colony, Queen}
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.environment.adapter.Cell
import scala.model.environment.EnvironmentManager.evolution
import scala.model.environment.EnvironmentManager
import scala.util.Random.{nextInt => randomUntil}
import scala.utility.Point
import scala.utility.SugarBowl.RichMappable

/**
 * Concrete implementation of the Ecosystem.
 *
 * @param environmentManager the environment manager.
 * @param colonies           a variable number of colonies.
 */
private[model] case class Ecosystem(environmentManager: EnvironmentManager, colonies: Colony*)

/**
 * Object that represents the Ecosystem.
 */
private[model] object Ecosystem {

  /**
   * Apply method for the Ecosystem.
   *
   * @param coloniesCount the number of initial colonies.
   * @param width         the width of the environment.
   * @param height        the height of the environment.
   * @return a new Ecosystem.
   */
  def apply(coloniesCount: Int, width: Int, height: Int): Ecosystem = {
    EnvironmentManager(width, height) ~> (
      manager => Ecosystem(manager, (1 to coloniesCount).map(_ => createColony(manager)()): _*))
  }

  /**
   * Method to update the Ecosystem.
   *
   * @param ecosystem the old Ecosystem.
   * @return a new Ecosystem, updated at the current iteration.
   */
  def update(ecosystem: Ecosystem): Ecosystem = Ecosystem(evolution(ecosystem environmentManager),
    Colony.manage(ecosystem.colonies.flatMap(_.update(Time incrementValue)(ecosystem environmentManager))
      .filter(_ isColonyAlive) toList).filter(_ isColonyAlive): _*)

  /**
   * Method to create a colony.
   *
   * @param environment the EnvironmentManager.
   * @param position    the initial position of a colony.
   * @return a the new colony.
   */
  def createColony(
    environment: EnvironmentManager
  )(
    position: Point = (randomUntil(environment width), randomUntil(environment height))
  ): Colony = {
    val genotype = Genotype()
    val cell: Cell = environment.cells()(position.x, position.y)
    val queen: Queen = Queen(None, genotype, genotype expressInPhenotype, 0, position, this.createColony(environment),
      EnvironmentInformation(Seq(cell)))
    queen.colony
  }
}
