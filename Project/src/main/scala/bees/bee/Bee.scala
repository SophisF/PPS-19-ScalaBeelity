package bees.bee

import bees.genotype.Genotype.Genotype
import bees.phenotype.CharacteristicTaxonomy
import bees.phenotype.Phenotype.Phenotype

object Bee {

  trait Bee{
    val genotype: Genotype
    val phenotype: Phenotype
    def update(): Unit
    def isAlive: Boolean
    def kill(): Unit
  }

  case class BeeImpl(override val genotype: Genotype, override val phenotype: Phenotype) extends Bee{
    protected var age: Int = 0
    protected var remainingDaysOfLife: Int = this.phenotype.getCharacteristicByTaxonomy(CharacteristicTaxonomy.LONGEVITY) match {
      case Some(value) => value.expression.asInstanceOf[Int]
      case _ => 0
    }

    override def isAlive: Boolean = this.remainingDaysOfLife == 0

    //Possibly to return a Bee
    override def kill(): Unit = this.remainingDaysOfLife = 0

    //example. Parameters from simulation for update of N days
    override def update(): Unit = {
      this.age += 1

      //based on environment characteristic
      this.remainingDaysOfLife -= 1
    }
  }


}
