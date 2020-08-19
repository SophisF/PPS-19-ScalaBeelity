package scala.model.bees.bee

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.phenotype.CharacteristicTaxonomy

/**
 * Object that represents bee
 */
object Bee {

  /**
   * Trait that represents bee
   */
  trait Bee{

    val genotype: Genotype
    val phenotype: Phenotype

    protected var age: Int = 0
    protected var remainingDaysOfLife: Int = this.phenotype.characteristicByTaxonomy(CharacteristicTaxonomy.LONGEVITY) match {
      case Some(value) => value.expression.asInstanceOf[Int]
      case _ => 0
    }
    def update(): Unit
    def isAlive: Boolean
    def kill(): Unit
  }

  /**
   * Class that represents bee
   * @param genotype genotype of the bee
   * @param phenotype phenotype of the bee
   */
  case class BeeImpl(override val genotype: Genotype, override val phenotype: Phenotype) extends Bee{


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
