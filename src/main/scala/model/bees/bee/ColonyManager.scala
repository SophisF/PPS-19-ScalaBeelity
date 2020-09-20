package model.bees.bee

import scala.model.bees.bee.{CollisionManager, Colony}
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.CharacteristicTaxonomy

object ColonyManager {

  private val maxAggressionToMerge: Int = 3
  private val minAggressionToAttack: Int = 8

  /**
   * Method to manage the colonies. It combines and adjust them if there are the conditions.
   *
   * @param colonies , the colonies of bees.
   * @return a new list of colonies with the new colonies managed.
   */
  def manage(colonies: List[Colony]): List[Colony] = {
    val combined = combine(colonies, colonies)
    fight(combined, combined, List.empty)
  }

  /**
   * Combines a colony with all the other that colliding with it and respect some conditions.
   *
   * @param col         the initial colonies.
   * @param newColonies the new colonies after the combinations.
   * @return the new colonies.
   */
  @scala.annotation.tailrec
  private def combine(col: List[Colony], newColonies: List[Colony]): List[Colony] = col match {
    case h :: t => val colliding = CollisionManager.findColliding(h, newColonies)
      val toMerge = colliding.filter(checkMerge(h, _))

      /**
       * Method to merge all the colony of a list with another colony.
       *
       * @param colony          the colony.
       * @param coloniesToMerge the list of colonies
       * @return the new colony after the merge.
       */
      @scala.annotation.tailrec
      def mergeAll(colony: Colony, coloniesToMerge: List[Colony]): Colony = coloniesToMerge match {
        case h :: t => mergeAll(merge(colony, h), t)
        case _ => colony
      }

      val merged = mergeAll(h, toMerge)
      combine(t diff toMerge, merged :: (newColonies diff (h :: toMerge)))
    case _ => newColonies
  }

  /**
   * Method to adjust the number of bees in the colonies if some conditions take place.
   *
   * @param colonies    the colonies of bees.
   * @param col         the initial colonies.
   * @param newColonies the new colonies.
   * @return the new colonies.
   */
  @scala.annotation.tailrec
  private def fight(colonies: List[Colony], col: List[Colony], newColonies: List[Colony]): List[Colony] = col match {
    case h :: t => val colliding = CollisionManager.findColliding(h, colonies)
      val toFight = colliding.filter(checkFight)

      /**
       * Method to adjust the number of bees of a colony with respect to each other that collide with it and under some conditions.
       *
       * @param colony   the colony.
       * @param colonies the other colonies.
       * @return a new colony with the new number of bees.
       */
      @scala.annotation.tailrec
      def fightAll(colony: Option[Colony], colonies: List[Colony]): Option[Colony] = colony match {
        case Some(colony) =>
          colonies
          match {
            case h :: t => fightAll(kill(colony, h), t)
            case _ => Some(colony)
          }

        case _ => None

      }

      val fought = fightAll(Some(h), toFight)
      fight(colonies, t, (if (fought.nonEmpty) fought.get :: newColonies else newColonies))
    case _ => newColonies.filter(_.isColonyAlive)
  }

  /**
   * Method to check if there are the conditions to merge two colony.
   *
   * @param colony1 the first colony
   * @param colony2 the second colony
   * @return true if the colonies can be merged, false otherwise.
   */
  private def checkMerge(colony1: Colony, colony2: Colony): Boolean = {
    val aggressionColony1: Int = EvolutionManager calculateAveragePhenotype colony1.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
    val aggressionColony2: Int = EvolutionManager calculateAveragePhenotype colony1.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
    aggressionColony1 < maxAggressionToMerge &&
      aggressionColony2 < maxAggressionToMerge
  }

  /**
   * Method to check if a colony respect the condition to fight against other colonies.
   *
   * @param colony the colony.
   * @return true if the colony can fight, false otherwise.
   */
  private def checkFight(colony: Colony): Boolean = {
    val aggression: Int = EvolutionManager calculateAveragePhenotype colony.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
    aggression > minAggressionToAttack
  }

  /**
   * Defines how two colony can be merged.
   *
   * @param colony1 the first colony.
   * @param colony2 the second colony.
   * @return a new colony, made up by the two colonies merged together.
   */
  private def merge(colony1: Colony, colony2: Colony): Colony = {
    Colony(if (colony1.area > colony2.area) colony1.color else colony2.color, if (colony1.area > colony2.area) colony1.queen else colony2.queen, colony1.bees ++ colony2.bees)
  }

  /**
   * Define how to adjust the number of bees of a colony.
   *
   * @param colony1 the colony to adjust.
   * @param colony2 the colony to fight.
   * @return a new colony with the number of bees adjusted.
   */
  private def kill(colony1: Colony, colony2: Colony): Option[Colony] = {
    val collisionArea = CollisionManager.collisionArea(colony1, colony2)
    val colony2Aggression: Int = if (colony2.isColonyAlive) EvolutionManager.calculateAveragePhenotype(colony2.bees).expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE) else 0
    val newBees = colony1.bees diff colony1.bees.take(colony2Aggression * collisionArea)
    if (newBees.nonEmpty) Some(Colony(colony1.color, colony1.queen, newBees)) else None

  }
}
