package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.adapter.Cell
import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.model.environment.EnvironmentManager
import scala.model.prolog.{MovementLogic, PrologEngine}
import scala.util.Random
import scala.utility.Point
import scala.model.prolog.PrologEngine._
import scala.model.bees.phenotype.Characteristic._


/**
 * Object that represents colony
 */
object Colony {

  type Color = Double

  def apply(color: Color = Random.nextDouble(), queen: Queen, bees: Seq[Bee]): Colony = ColonyImpl(color, queen, bees)

  private val limitBeesForCell: Int = 10

  /**
   * Trait that represents colony
   */
  trait Colony {


    val queen: Queen
    val color: Color


    val dimension: Int
    val bees: Seq[Bee]
    val maxBees: Int
    val position: Point
    val area: Int

    val isColonyAlive: Boolean
    val isQueenAlive: Boolean
    val numberOfBees: Int

    def update(time: Int)(environmentManager: EnvironmentManager): List[Colony]

    def move(time: Int)(environmentManager: EnvironmentManager): Point

  }

  //TODO require bees non empty
  case class ColonyImpl(override val color: Color, override val queen: Queen, override val bees: Seq[Bee] = List.empty) extends Colony {

    // println(bees.size)

    override lazy val position: Point = this.queen.position

    override lazy val dimension: Int = (math.ceil(math.sqrt(this.bees.size.toDouble / limitBeesForCell)) / 2.0).toInt

    override lazy val area: Int = (this.dimension * 2 + 1) ^ 2

    override lazy val isColonyAlive: Boolean = this.bees.exists(_.isAlive)

    override lazy val isQueenAlive: Boolean = this.queen.isAlive

    override lazy val numberOfBees: Int = this.bees.size

    override def move(time: Int)(environmentManager: EnvironmentManager): Point = {
      val reachableCells = for {
        i <- this.queen.position.x - time * this.averagePhenotype.expressionOf(CharacteristicTaxonomy.SPEED) to this.queen.position.x + time * this.averagePhenotype.expressionOf(CharacteristicTaxonomy.SPEED)
        j <- this.queen.position.y - time * this.averagePhenotype.expressionOf(CharacteristicTaxonomy.SPEED) to this.queen.position.y + time * this.averagePhenotype.expressionOf(CharacteristicTaxonomy.SPEED)
        if i - this.dimension >= 0 && i + this.dimension < environmentManager.environment.map.rows &&
          j - this.dimension >= 0 && j + this.dimension < environmentManager.environment.map.cols
      } yield PrologEngine.buildCellTerm(environmentManager.cells().valueAt(i, j), Point(i, j))

      MovementLogic.solveLogic(reachableCells,
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY)),
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY)),
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))) getOrElse this.queen.position

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
      val newColony = this.generateColony(time)(environmentManager)
      Colony(this.color, if (queen.isAlive) queen else {
        val similarGenotype = EvolutionManager.calculateAverageGenotype(bees)
        Queen(Some(this), similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), 0,
          temperature, pressure, humidity, newCenter, this.queen.generateNewColony)
      }, bees) :: newColony.getOrElse(List.empty)


    }

    //TODO refactor with function passing
    private def calculateCells(newCenter: Point)(environmentManager: EnvironmentManager): Seq[Cell] = {
      for {
        i <- newCenter.x - this.dimension to newCenter.x + this.dimension
        j <- newCenter.y - this.dimension to newCenter.y + this.dimension
        if i - this.dimension >= 0 && i + this.dimension < environmentManager.environment.map.rows &&
          j - this.dimension >= 0 && j + this.dimension < environmentManager.environment.map.cols
      } yield environmentManager.cells().valueAt(i, j)
    }

    private def updatePopulation(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      this.bees.toList.filter(_.isAlive).map(_.update(time)(temperature)(pressure)(humidity)) ++ generateBees(time)(temperature)(pressure)(humidity)

    }

    private def generateBees(time: Int)(temperature: Int)(pressure: Int)(humidity: Int): List[Bee] = {
      val r: Int = this.averagePhenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)
      val max: Int = if (this.numberOfBees >= this.maxBees) 0 else r * time
      Random.shuffle(this.bees).flatMap(bee => (0 to bee.phenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)).map(_ => {

        val similarGenotype = EvolutionManager.buildGenotype(bee.genotype)(bee.phenotype)(temperature)(pressure)(humidity)(time)

        Bee(similarGenotype, Phenotype(Genotype.calculateExpression(similarGenotype)), Random.nextInt(time), temperature, pressure, humidity)
      })).take(max).toList

    }

    private def generateColony(time: Int)(environmentManager: EnvironmentManager): Option[List[Colony]] = {
      // println("generate")
      if (Random.nextInt(10000 / time) < 1) Some(List(this.queen.generateNewColony(environmentManager.proximityOf(this.queen.position)))) else None
    }

    /*
    private def proximity(): Point = {
      Point(this.queen.position.x + (this.dimension + Random.nextInt(20)) * (if (Random.nextInt() % 2 == 0) 1 else -1), this.queen.position.y + (this.dimension + Random.nextInt(20)) * (if (Random.nextInt() % 2 == 0) 1 else -1))
    }

     */


    private val averagePhenotype: Phenotype = EvolutionManager.calculateAveragePhenotype(this.bees)
    override val maxBees: Int = {
      val r: Int = this.averagePhenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)
      r * 100
    }
  }

}