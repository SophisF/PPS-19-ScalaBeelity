package scala.model.bees.bee

import model.bees.bee.EvolutionManager

import scala.model.bees.bee.Bee.{Bee, BeeImpl}
import scala.model.bees.bee.ColonyArea.{ColonyArea, ColonyAreaImpl}
import scala.model.bees.bee.Queen.{Queen, QueenImpl}
import scala.model.environment.matrix.Point

/**
 * Object that represents colony
 */
object Colony {

  def update(time: Int, bees: Set[Bee]): Set[Bee] = {val p: Set[Bee] = Set.from(bees.map(bee => BeeImpl(bee.genotype, bee.phenotype, bee.age + time, 20, 990, 65))); p}

  private val limitBeesForCell: Int = 10

  /**
   * Trait that represents colony
   */
  trait Colony {
    val center: Point
    def getQueen: Queen
    def setNewQueen(queen: Queen): Unit
    def getDimension: Int
    def bees: List[Bee]
    def +(bees: Bee*): Unit
    def isColonyAlive: Boolean
    def isQueenAlive: Boolean
    def update(time: Int): Unit
    def collideWith(colony: Colony): Boolean
  }

  /**
   * Class that represents colony
   * @param queenBee the only queen of the colony
   */
  case class ColonyImpl(private val queenBee: Queen, override val center: Point) extends Colony{

    private var queen: Queen = queenBee
    private var _bees: List[Bee] = List.empty
    private var averageGenotype = EvolutionManager.calculateAverageGenotype(this._bees)
    private var averagePhenotype = EvolutionManager.calculateAveragePhenotype(this.averageGenotype)
    private var dimension: Int = 0
    private var area: ColonyArea = ColonyAreaImpl(this.center, dimension)

    override def getQueen: Queen = this.queen

    override def setNewQueen(queen: Queen): Unit = this.queen = queen

    override def bees: List[Bee] = this._bees

    override def getDimension: Int = this.dimension

    override def +(bees: Bee*): Unit = this._bees = this._bees ++ _bees

    override def isColonyAlive: Boolean = this.isQueenAlive && this._bees.exists(_.isAlive)

    override def isQueenAlive: Boolean = this.queen.isAlive

    override def update(time: Int): Unit = {
      this._bees = this.bees.map(bee => BeeImpl(bee.genotype, bee.phenotype, bee.age + time, 20, 1000, 65))
      this.queen = QueenImpl(Some(this.queen.colony), this.queen.genotype, this.queen.phenotype, this.queen.age + time, 20, 1000, 65)



    }

    override def collideWith(colony: Colony): Boolean = false

  }

}
