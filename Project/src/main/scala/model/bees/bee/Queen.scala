package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.Colony
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.{Genotype, GenotypeImpl}
import scala.model.bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}

/**
 * Object that represents the queen bee
 */
object Queen {

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {

    val genotype: Genotype
    val phenotype: Phenotype
    def move(): Unit
    def generateBee()
    def getAge: Int
    def update()
  }


  case class QueenImpl(colony: Colony) extends Queen {
    override val genotype: Genotype = GenotypeImpl()
    override val phenotype: Phenotype = PhenotypeImpl(Genotype.calculateExpression(genotype))

    override def move(): Unit = ???

    override def generateBee(): Unit = ???

    override def getAge: Int = this.age

    override def update(time: Int): Unit = ???

    override def isAlive: Boolean = super.isAlive

    override def kill(): Unit = super.kill()

  }

}
