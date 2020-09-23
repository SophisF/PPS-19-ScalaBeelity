package scala.model.bees.bee

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}

/**
 * A combiner object for the colonies. If two colonies come into contact and their aggression is lower
 * than a threshold, it merge the colonies.
 */
object Combiner {
  /**
   * Maximum level of aggression to merge two colonies.
   */
  private val maxAggressionToMerge: Int = 3

    /**
     * Combines a colony with all the other that colliding with it and respect some conditions.
     *
     * @param col         the initial colonies.
     * @param newColonies the new colonies after the combinations.
     * @return the new colonies.
     */
    @scala.annotation.tailrec
    def combine(col: Iterable[Colony], newColonies: Iterable[Colony]): Iterable[Colony] = col match {
      case h :: t => val colliding = CollisionManager.findColliding(h, newColonies)
        val toMerge = colliding.filter(checkMerge(h, _)).toList

        /**
         * Method to merge all the colony of a list with another colony.
         *
         * @param colony          the colony.
         * @param coloniesToMerge the list of colonies
         * @return the new colony after the merge.
         */
        @scala.annotation.tailrec
        def mergeAll(colony: Colony, coloniesToMerge: Iterable[Colony]): Colony = coloniesToMerge match {
          case h :: t => mergeAll(merge(colony, h), t)
          case _ => colony
        }

        val merged = mergeAll(h, toMerge)
        combine(t diff toMerge, merged :: (newColonies.toList diff (h :: toMerge)))
      case _ => newColonies
    }

    /**
     * Method to check if there are the conditions to merge two colony.
     *
     * @param colony1 the first colony
     * @param colony2 the second colony
     * @return true if the colonies can be merged, false otherwise.
     */
    private def checkMerge(colony1: Colony, colony2: Colony): Boolean = {
      val aggressionColony1: Int = Phenotype averagePhenotype colony1.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
      val aggressionColony2: Int = Phenotype averagePhenotype colony1.bees expressionOf CharacteristicTaxonomy.AGGRESSION_RATE
      aggressionColony1 < maxAggressionToMerge &&
        aggressionColony2 < maxAggressionToMerge
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
}
