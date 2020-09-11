package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.environment.matrix.Point
import scala.util.Random

/**
 * Object that represents colony
 */
object Colony {

  def apply(queen: Queen, bees: Seq[Bee]): Colony = ColonyImpl(queen, bees)

  private val limitBeesForCell: Int = 10

  /**
   * Trait that represents colony
   */
  trait Colony {

    val queen: Queen


    val dimension: Int
    val bees: Seq[Bee]
    val maxBees: Int = (this.bees.map(_.effectiveReproductionRate).sum / this.bees.size) * 100
    val position: Point
    val area: Int

    val isColonyAlive: Boolean
    val isQueenAlive: Boolean

    def update(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): Colony

    def move: Point

  }


  case class ColonyImpl(override val queen: Queen, override val bees: Seq[Bee] = List.empty) extends Colony {

    //TODO overriding of equals


    //Da mettere in update
    //private var averageGenotype = EvolutionManager.calculateAverageGenotype(this._bees)
    //private var averagePhenotype = EvolutionManager.calculateAveragePhenotype(this.averageGenotype)


    override lazy val position: Point = this.queen.position

    override lazy val dimension: Int = (math.ceil(math.sqrt(this.bees.size.toDouble / limitBeesForCell)) / 2.0).toInt

    override lazy val area: Int = (this.dimension * 2 + 1) ^ 2

    override lazy val isColonyAlive: Boolean = this.isQueenAlive && this.bees.exists(_.isAlive)

    override lazy val isQueenAlive: Boolean = this.queen.isAlive

    //TODO
    override def move: Point = this.queen.position

    override def update(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): Colony = {
      val newCenter = this.move
      val bees = this.updatePopulation(time)(temperature)(pressure)(humidity)
      val queen = Queen(Some(this), this.queen.genotype, this.queen.phenotype, this.queen.age + time,
        temperature, pressure, humidity, newCenter)
      ColonyImpl(if (queen.isAlive) queen else {
        val similarGenotype = EvolutionManager.calculateAverageGenotype(bees)
        Queen(Some(this), similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), 0,
          temperature, pressure, humidity, newCenter)
      }, bees)
    }

    private def updatePopulation(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      this.bees.toList.filter(_.isAlive).map(_.update(time, temperature, pressure, humidity)) ++
        this.generateBees(time)(temperature)(pressure)(humidity)
    }

    private def generateBees(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      if(this.queen.canGenerate > 0){
        this.bees.flatMap(bee => List.range(0, bee.canGenerate).map(_ => {
          val similarGenotype = EvolutionManager.calculateAverageGenotype(List(bee, this.queen))
          Bee(similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), Random.nextInt(time), temperature, pressure, humidity)
        })).toList
      }
      else List.empty
    }


  }

}
