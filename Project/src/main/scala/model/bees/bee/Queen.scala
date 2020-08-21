package scala.model.bees.bee

import scala.model.Ecosystem
import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.{Colony, ColonyImpl}
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.util.Random

/**
 * Object that represents the queen bee
 */
object Queen {

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
    val colony: Colony
    def move(): Unit
    def generateBee()
  }

  case class QueenImpl(colonyOpt: Option[Colony],
  override val genotype: Genotype, override val phenotype: Phenotype,
  override val age: Int, temperature: Int,
  pressure: Int, humidity: Int) extends Queen {

    override val colony: Colony = colonyOpt getOrElse ColonyImpl(this, (Random.nextInt(Ecosystem.width), Random.nextInt(Ecosystem.height)))


    override def move(): Unit = ()
    override def generateBee(): Unit = ()


    override val remainingDaysOfLife: Int = Bee.calculateRemainingLife(this, temperature, pressure, humidity)

  }

}
