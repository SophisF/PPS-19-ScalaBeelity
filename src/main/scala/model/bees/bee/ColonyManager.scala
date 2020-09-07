package model.bees.bee

import scala.model.bees.bee.Colony.Colony

object ColonyManager {

  private val maxAggressionToMerge: Int = 4
  private val minAggressionToAttack: Int = 7

  def merge(colony1: Colony, colony2: Colony): Option[Colony] = {
    if(EvolutionManager.calculateAveragePhenotype(colony1.bees).aggression.expression < maxAggressionToMerge
    &&
    EvolutionManager.calculateAveragePhenotype(colony2.bees).aggression.expression < maxAggressionToMerge){
      if(colony1.area < colony2.area) Some(colony1) else Some(colony2)
    }
    else deadBees(colony1, colony2)
  }

  def deadBees(colony1: Colony, colony2: Colony): Option[Colony] = {
    val avgAggressionColony1 = EvolutionManager.calculateAveragePhenotype(colony1.bees).aggression.expression
    val avgAggressionColony2 =  EvolutionManager.calculateAveragePhenotype(colony2.bees).aggression.expression
    if((avgAggressionColony1 > minAggressionToAttack || avgAggressionColony2 > minAggressionToAttack)
      && avgAggressionColony1 != avgAggressionColony2){
      if(avgAggressionColony1 < avgAggressionColony2) Some(colony1) else Some(colony2)
    }
    else None
  }

}
