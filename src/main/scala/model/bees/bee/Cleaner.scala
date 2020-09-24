package scala.model.bees.bee

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}

/**
 * A cleaner object for the colonies. If two colonies come into contact it provides for remove the
 * dead bees.
 */
object Cleaner {

  private val minAggressionToAttack: Int = 8

  /**
   * Method to adjust the number of bees in the colonies if some conditions take place.
   *
   * @param colonies    the colonies of bees.
   * @param col         the initial colonies.
   * @param newColonies the new colonies.
   * @return the new colonies.
   */
  @scala.annotation.tailrec
  def clean(colonies: Iterable[Colony], col: Iterable[Colony], newColonies: Iterable[Colony]): Iterable[Colony] = col match {
    case h :: t => val colliding = CollisionManager.findColliding(h, colonies)
      val toClean = colliding.filter(checkClean)
      /**
       * Method to adjust the number of bees of a colony with respect to each other that collide with it and under some conditions.
       *
       * @param colony   the colony.
       * @param colonies the other colonies.
       * @return a new colony with the new number of bees.
       */
      @scala.annotation.tailrec
      def cleanAll(colony: Option[Colony], colonies: Iterable[Colony]): Option[Colony] = colony match {
        case Some(colony) =>
          colonies
          match {
            case h :: t => cleanAll(kill(colony, h), t)
            case _ => Some(colony)
          }

        case _ => None
      }
      val cleaned = cleanAll(Some(h), toClean)
      clean(colonies, t, cleaned.orNull :: newColonies.toList)
    case _ => newColonies.filter(_.isColonyAlive)
  }

  /**
   * Method to check if a colony respect the condition to fight against other colonies.
   *
   * @param colony the colony.
   * @return true if the colony can fight, false otherwise.
   */
  private def checkClean(colony: Colony): Boolean = {
    val aggression: Int = Phenotype averagePhenotype colony.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
    aggression > minAggressionToAttack
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
    val colony2Aggression: Int = if (colony2.isColonyAlive) Phenotype.averagePhenotype(colony2.bees).expressionOf(CharacteristicTaxonomy.AGGRESSION_RATE) else 0
    val newBees = colony1.bees diff colony1.bees.take(colony2Aggression * collisionArea)
    if (newBees.nonEmpty) Some(Colony(colony1.color, colony1.queen, newBees)) else None

  }
}
