package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.EnvironmentManager
import scala.model.environment.matrix.Point
import scala.model.prolog.{MovementLogic, PrologEngine}
import scala.util.Random
import scala.model.bees.utility.PimpTuple._
import scala.model.adapter.Cell

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
    val maxBees: Int
    val position: Point
    val area: Int
    val isColonyAlive: Boolean
    val isQueenAlive: Boolean
    val numberOfBees: Int

    def update(time: Int)(environmentManager: EnvironmentManager): List[Colony]

  }


  case class ColonyImpl(override val queen: Queen, override val bees: Seq[Bee] = List.empty) extends Colony {

    //TODO overriding of equals


    //Da mettere in update
    //private var averageGenotype = EvolutionManager.calculateAverageGenotype(this._bees)
    //private var averagePhenotype = EvolutionManager.calculateAveragePhenotype(this.averageGenotype)

    private val averagePhenotype: Phenotype = EvolutionManager.calculateAveragePhenotype(this.bees)

    override lazy val position: Point = this.queen.position

    override lazy val dimension: Int = (math.ceil(math.sqrt(this.bees.size.toDouble / limitBeesForCell)) / 2.0).toInt

    override lazy val area: Int = (this.dimension * 2 + 1) ^ 2

    override lazy val isColonyAlive: Boolean = /*this.isQueenAlive && */this.bees.exists(_.isAlive)

    override lazy val isQueenAlive: Boolean = this.queen.isAlive

    override lazy val numberOfBees: Int = this.bees.size

    override lazy val maxBees: Int = this.averagePhenotype.reproductionRate.expression * 100

    private def move(time: Int)(environmentManager: EnvironmentManager): Point = {
      val reachableCells = for {
        i <- this.queen.position.x - time * this.averagePhenotype.speed.expression to this.queen.position.x + time * this.averagePhenotype.speed.expression
        j <- this.queen.position.y - time * this.averagePhenotype.speed.expression to this.queen.position.y + time * this.averagePhenotype.speed.expression
        if i - this.dimension > 0 && i + this.dimension < environmentManager.environment.map.rows &&
          j - this.dimension > 0 && j + this.dimension < environmentManager.environment.map.cols
      } yield PrologEngine.buildCellTerm(environmentManager.environment.map.valueAt(i, j), Point(i, j))
      println(reachableCells.size)
      MovementLogic.solveLogic(reachableCells, this.queen.phenotype.temperatureCompatibility.expression, this.queen.phenotype.pressureCompatibility.expression, this.queen.phenotype.humidityCompatibility.expression)
    }


    override def update(time: Int)(environmentManager: EnvironmentManager): List[Colony] = {
      val newCenter: Point = this.move(time)(environmentManager)
      val cells = this.calculateCells(newCenter)(environmentManager)

      val temperature: Int = cells.foldRight(0)(_.temperature + _) / cells.size
      val pressure: Int = cells.foldRight(0)(_.pressure + _) / cells.size
      val humidity: Int = cells.foldRight(0)(_.humidity + _) / cells.size


      val bees = this.updatePopulation(time)(temperature)(pressure)(humidity)
      val queen = Queen(Some(this), this.queen.genotype, this.queen.phenotype, this.queen.age + time,
        temperature, pressure, humidity, newCenter, this.queen.generateNewColony)
      val newColony = this.generateColony
      Colony(if (queen.isAlive) queen else {
        val similarGenotype = EvolutionManager.calculateAverageGenotype(bees)
        Queen(Some(this), similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), 0,
          temperature, pressure, humidity, newCenter, this.queen.generateNewColony)
      }, bees) :: (if (newColony.nonEmpty) List(newColony.get) else List.empty)


    }

    private def calculateCells(newCenter: Point)(environmentManager: EnvironmentManager): Seq[Cell] = {
      for {
        i <- newCenter.x - this.dimension to newCenter.x + this.dimension
        j <- newCenter.y - this.dimension to newCenter.y + this.dimension
      } yield environmentManager.cells().valueAt(i, j)
    }

    private def updatePopulation(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      this.bees.toList.filter(_.isAlive).map(_.update(time)(temperature)(pressure)(humidity)) ++ generateBees(time)(temperature)(pressure)(humidity)

    }

    private def generateBees(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      if (this.queen.canGenerate) {
        Random.shuffle(this.bees).take(this.averagePhenotype.reproductionRate.expression).flatMap(bee => List.range(0, this.averagePhenotype.reproductionRate.expression * time).map(_ => {
          val similarGenotype = EvolutionManager.calculateAverageGenotype(List(bee, this.queen))
          Bee(similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), Random.nextInt(time), temperature, pressure, humidity)
        }))
        }.toList
      else List.empty
    }

    private def generateColony: Option[Colony] = {
      // println("generate")
      if (Random.nextInt(100000) < 1) Some(this.queen.generateNewColony(this.proximity())) else None
    }

    private def proximity(): Point = {
      Point(this.queen.position.x + (this.dimension + Random.nextInt(20)) * (if (Random.nextInt() % 2 == 0) 1 else -1), this.queen.position.y + (this.dimension + Random.nextInt(20)) * (if (Random.nextInt() % 2 == 0) 1 else -1))
    }
  }

}
