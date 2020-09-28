package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.bee.utility.{Cleaner, Combiner, EvolutionManager}
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Characteristic._
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.phenotype.{CharacteristicTaxonomy, Phenotype}
import scala.model.environment.EnvironmentManager
import scala.model.prolog.PrologEngine._
import scala.model.prolog.{MovementLogic, PrologEngine}
import scala.util.Random
import scala.utility.Point
import scala.utility.PimpInt._
import scala.utility.PimpIterable._


/**
 * Object that represents colony
 */
object Colony {

  /**
   * Type that represents a color.
   */
  type Color = Double

  /**
   * Apply method for the colonies.
   *
   * @param color the color of the colony.
   * @param queen the queen of the colony.
   * @param bees  the sequence of bees.
   * @return a new colony.
   */
  def apply(color: Color = Random.nextDouble(), queen: Queen, bees: Set[Bee]): Colony = ColonyImpl(color, queen, bees)

  /**
   * Variable that represents the maximum number of bees per cell.
   */
  private val limitBeesForCell: Int = 5

  private val proximity: Int = 10

  private val newColonyGenerationProbability: Int = 10000

  /**
   * Method to manage the colonies. It combines and adjust them if there are the conditions.
   *
   * @param colonies , the colonies of bees.
   * @return a new list of colonies with the new colonies managed.
   */
  def manage(colonies: List[Colony]): List[Colony] = {
    val combined = Combiner.combine(colonies, colonies)
    Cleaner.clean(combined, combined, List.empty).toList
  }

  /**
   * Trait that represents colony
   */
  trait Colony {
    val color: Color
    val queen: Queen
    val bees: Set[Bee]

    /**
     * Getter method for the dimension of the colony expressed as the half of the side of a square, excepted the center.
     *
     * @return the dimension of the colony.
     */
    def dimension: Int

    /**
     * Getter method for the maximum number of bees for the colony.
     *
     * @return the maximum number of bees for the colony.
     */
    def maxBees: Int

    /**
     * Getter method for the center of the colony, expressed as the position of the queen.
     *
     * @return the center of the colony.
     */
    def center: Point

    /**
     * Getter method for the area of the colony, expressed as the number of cells occupied.
     *
     * @return the area of the colony.
     */
    def area: Int

    /**
     * Check if the colony is alive.
     *
     * @return true if at least a bee is still alive, false otherwise.
     */
    def isColonyAlive: Boolean

    /**
     * Check if the queen of the colony is alive.
     *
     * @return true if she's alive, false otherwise.
     */
    def isQueenAlive: Boolean

    /**
     * Getter method for the number of bees in the colony.
     *
     * @return the current number of bees of the colony.
     */
    def numberOfBees: Int

    /**
     * Update method for the colonies.
     *
     * @param time               the time that has passed from the last iteration.
     * @param environmentManager the environmentManager of the simulation.
     * @return a list of colony, that contains the colony and the new colonies generated by this one.
     */
    def update(time: Int)(environmentManager: EnvironmentManager): List[Colony]

  }

  case class ColonyImpl(override val color: Color, override val queen: Queen, override val bees: Set[Bee]) extends Colony {
    require(bees.nonEmpty)

    /**
     * Utility variable to maintain the average phenotype of the colony.
     */
    private lazy val averagePhenotype: Phenotype = Phenotype.averagePhenotype(Set(this.queen) ++ this.bees)

    override def center: Point = this.queen.position

    override def dimension: Int = (math.ceil(math.sqrt(this.bees.size.toDouble / limitBeesForCell)) / 2.0).toInt

    override def area: Int = (this.dimension * 2 + 1) ~^ 2

    override def isColonyAlive: Boolean = this.bees.exists(_.isAlive)

    override def isQueenAlive: Boolean = this.queen.isAlive

    override def numberOfBees: Int = this.bees.size

    override def maxBees: Int = {
      val r: Int = this.averagePhenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)
      r * 100
    }

    override def update(time: Int)(environmentManager: EnvironmentManager): List[Colony] = {
      val newCenter: Point = this.move(time)(environmentManager)
      val cells = environmentManager.indexInRange(newCenter.x.applyTwoOperations(this.dimension)(_ - _)(_ + _),
        newCenter.y.applyTwoOperations(this.dimension)(_ - _)(_ + _))
        .map(index => environmentManager.cells().valueAt(index._1, index._2))

      val temperature: Int = cells.map(_.temperature).average
      val pressure: Int = cells.map(_.pressure).average
      val humidity: Int = cells.map(_.humidity).average

      Colony(this.color, this.updateQueen(time)(temperature)(pressure)(humidity)(newCenter),
        this.updatePopulation(time)(temperature)(pressure)(humidity)) ::
        (this.generateColony(time)(environmentManager) getOrElse List.empty)
    }

    /**
     * Method that manages the movement of the colony.
     *
     * @param time               the time that has passed from the last iteration.
     * @param environmentManager the environmentManager of the simulation.
     * @return the new point where the queen has to move in.
     */
    private def move(time: Int)(environmentManager: EnvironmentManager): Point = {
      val toApply = time * this.averagePhenotype.expressionOf(CharacteristicTaxonomy.SPEED)
      val reachableCells = environmentManager.indexInRange(this.center.x.applyTwoOperations(toApply)(_ - _)(_ + _),
        this.center.y.applyTwoOperations(toApply)(_ - _)(_ + _))
        .map(index => PrologEngine.buildCellTerm(environmentManager.cells().valueAt(index._1, index._2),
          Point(index._1, index._2)))

      MovementLogic.solveLogic(reachableCells,
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY)),
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY)),
        toTuple(this.queen.phenotype.expressionOf(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))) getOrElse this.queen.position

    }

    /**
     * Method to update the queen.
     *
     * @param time               the time that has passed from the last iteration.
     * @param averageTemperature the average temperature of the environment where the bee's colony is.
     * @param averagePressure    the average pressure of the environment where the bee's colony is.
     * @param averageHumidity    the average humidity of the environment where the bee's colony is.
     * @param newCenter          the new center of the colony.
     * @return a new queen.
     */
    private def updateQueen(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int)(newCenter: Point): Queen = {
      val queen = this.queen.update(time)(averageTemperature)(averagePressure)(averageHumidity)(newCenter)
      if (queen.isAlive) queen else {
        val similarGenotype = Genotype.averageGenotype(bees)
        Queen(Some(this), similarGenotype, similarGenotype expressInPhenotype, 0,
          averageTemperature, averagePressure, averageHumidity, newCenter, this.queen.generateNewColony)
      }
    }

    /**
     * Method to update the bees.
     *
     * @param time               the time that has passed from the last iteration.
     * @param averageTemperature the average temperature of the environment where the bee's colony is.
     * @param averagePressure    the average pressure of the environment where the bee's colony is.
     * @param averageHumidity    the average humidity of the environment where the bee's colony is.
     * @return a new set of bees.
     */
    private def updatePopulation(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int): Set[Bee] = {
      this.bees.filter(_.isAlive).map(_.update(time)(averageTemperature)(averagePressure)(averageHumidity)) ++
        generateBees(time)(averageTemperature)(averagePressure)(averageHumidity)
    }

    /**
     * Method to generate new bees every iteration, if it's possible.
     *
     * @param averageTemperature the average temperature of the environment where the bee's colony is.
     * @param averagePressure    the average pressure of the environment where the bee's colony is.
     * @param averageHumidity    the average humidity of the environment where the bee's colony is.
     * @return a new set of bees.
     */
    private def generateBees(time: Int)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int): Set[Bee] = {
      val r: Int = this.averagePhenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)
      val max: Int = if (this.numberOfBees >= this.maxBees) 0 else r * time
      Random.shuffle(this.bees).flatMap(bee => (0 to bee.phenotype.expressionOf(CharacteristicTaxonomy.REPRODUCTION_RATE)).map(_ => {
        val similarGenotype = EvolutionManager.evolveGenotype(bee.genotype)(averageTemperature)(averagePressure)(averageHumidity)(time)
        Bee(similarGenotype, similarGenotype expressInPhenotype, Random.nextInt(time),
          averageTemperature, averagePressure, averageHumidity)
      })).take(max)
    }

    private def generateColony(time: Int)(environmentManager: EnvironmentManager): Option[List[Colony]] = {
      if (Random.nextInt(newColonyGenerationProbability / time) < 1)
        Some(List(this.queen.generateNewColony(environmentManager.proximityOf(
          this.center.x.applyTwoOperations(proximity)(_ - _)(_ + _),
          this.center.y.applyTwoOperations(proximity)(_ - _)(_ + _)
        )))) else None
    }

  }

}