package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.Ecosystem
import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Colony.{Colony, ColonyImpl}
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.matrix.Point
import scala.util.Random

/**
 * Object that represents the queen bee
 */
object Queen {

  def apply(colonyOpt: Option[Colony],
            genotype: Genotype, phenotype: Phenotype,
            age: Int, temperature: Int,
            pressure: Int, humidity: Int,
            position: Point = (Random.nextInt(Ecosystem.width), Random.nextInt(Ecosystem.height))): Queen =
    QueenImpl(colonyOpt, genotype, phenotype, age, temperature, pressure, humidity, position)

  /**
   * Trait for a queen
   */
  trait Queen extends Bee {
    val colony: Colony
    val position: Point

    def move(): Unit

    def generateBee()
  }

  private case class QueenImpl(colonyOpt: Option[Colony],
                       override val genotype: Genotype, override val phenotype: Phenotype,
                       override val age: Int, temperature: Int,  pressure: Int, humidity: Int,
                               override val position: Point) extends Queen {

    override val colony: Colony = colonyOpt getOrElse ColonyImpl(this, List.range(0, 30).map(_ => {
      val similarGenotype = EvolutionManager.buildGenotype(this.genotype)(this.phenotype)(temperature)(pressure)(humidity)(1)

      Bee(
        similarGenotype,
        Phenotype(Genotype.calculateExpression(similarGenotype)),
        0,
        temperature, pressure, humidity
      )
    }))


    override def move(): Unit = ()

    override def generateBee(): Unit = ()


    override lazy val remainingDaysOfLife: Int = Bee.calculateRemainingLife(this, temperature, pressure, humidity)

  }

}
