package model.bees.bee

import model.bees.bee.Bee.Bee
import model.bees.genotype.Genotype
import model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import model.bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}

/**
 * Object that represents the queen bee
 */
object Queen {

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
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
    /*
    override val genotype: Genotype = GenotypeImpl(List.range(0, 100).map(_ => Gene()))
    override val phenotype: Phenotype = PhenotypeImpl(GeneExpressor.mapGenotypeToCharacteristics(genotype))

    private def generateColony() = ???

    private var remainingDaysOfLife: Int = this.phenotype.characteristicByTaxonomy(CharacteristicTaxonomy.LONGEVITY) match {
      case Some(value) => value..asInstanceOf[Int]
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

     */
    override val genotype: Genotype = GenotypeImpl()
    override val phenotype: Phenotype = PhenotypeImpl(Genotype.calculateExpression(genotype))

    override def move(): Unit = ???

    override def generateBee(): Unit = ???

    override def getAge: Int = this.age

    override def update(): Unit = ???

    override def isAlive: Boolean = ???

    override def kill(): Unit = ???
  }

}
