package bees.bee

import bees.bee.Bee.BeeImpl
import bees.genotype.Genotype.{Genotype, GenotypeImpl}
import bees.genotype.{Gene, GeneExpressor}
import bees.phenotype.CharacteristicTaxonomy
import bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}

/**
 * Object that represents the queen bee
 */
object Queen {

  /**
   * Trait for a queen
   */
  trait Queen extends BeeImpl {
    val position: (Int, Int)
    val genotype: Genotype
    val phenotype: Phenotype
    def move(): Unit
    def generateBee()
    def getAge: Int
    def update()
  }

  //position override between the environment dimensions
  /**
   * Class that represents a queen bee
   * @param position its position in the environment
   */
  case class QueenImpl(override val position: (Int, Int)) extends Queen {
    override val genotype: Genotype = GenotypeImpl(List.range(0, 100).map(_ => Gene()))
    override val phenotype: Phenotype = PhenotypeImpl(GeneExpressor.mapGenotypeToCharacteristics(genotype))

    private def generateColony() = ???

    private var remainingDaysOfLife: Int = this.phenotype.getCharacteristicByTaxonomy(CharacteristicTaxonomy.LONGEVITY) match {
      case Some(value) => value.expression.asInstanceOf[Int]
      case _ => 0
    }

    //add parameters from environment. Recap prolog
    override def move(): Unit = ???

    override def generateBee(): Unit = ???

    override def getAge: Int = this.age

    //example. Parameters from simulation for update of N days
    override def update(): Unit = {
      this.age += 1

      //based on environment characteristic
      this.remainingDaysOfLife -= 1
    }

    override def isAlive: Boolean = super.isAlive

    override def kill(): Unit = super.kill()
  }

}
