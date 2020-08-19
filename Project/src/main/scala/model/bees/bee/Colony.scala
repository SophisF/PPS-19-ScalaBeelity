package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.ColonyArea.{ColonyArea, ColonyAreaImpl}
import scala.model.bees.bee.Queen.Queen
import scala.model.environment.matrix.Point

/**
 * Object that represents colony
 */
object Colony {

  private val limitBeesForCell: Int = 10

  /**
   * Trait that represents colony
   */
  trait Colony {
    val center: Point
    def getQueen: Queen
    def setNewQueen(queen: Queen): Unit
    def getDimension: Int
    def getBees: Set[Bee]
    def +(bees: Bee*): Unit
    def isColonyAlive: Boolean
    def isQueenAlive: Boolean
    def update(): Unit
    def collideWith(colony: Colony): Boolean
  }

  /**
   * Class that represents colony
   * @param queenBee the only queen of the colony
   */
  case class ColonyImpl(private val queenBee: Queen, override val center: Point) extends Colony{
    private var queen: Queen = queenBee
    private var bees: Set[Bee] = Set.empty
    private var dimension: Int = 0
    private var area: ColonyArea = ColonyAreaImpl(this.center, dimension)

    override def getQueen: Queen = this.queen

    override def setNewQueen(queen: Queen): Unit = this.queen = queen

    override def getBees: Set[Bee] = this.bees

    override def getDimension: Int = this.dimension

    override def +(bees: Bee*): Unit = this.bees = this.bees ++ bees

    override def isColonyAlive: Boolean = this.isQueenAlive && this.bees.exists(_.isAlive)

    override def isQueenAlive: Boolean = this.queen.isAlive

    //based on environment parameters will call the difference update.
    //check problems with queen position
    /*
    override def update(): Unit = {
      if (bees.size > area*limitBeesForCell) {dimension+=1; area = ColonyAreaImpl(queen.position, dimension)}
      else if (bees.size < ColonyAreaImpl(queen.position, dimension-1)*limitBeesForCell) {dimension-=1; area = ColonyAreaImpl(queen.position, dimension)}
    }

    override def getPosition: Ends = area.getEnds()

    override def collideWith(colony: Colony): Boolean = ???

     */


    override def update(): Unit = {

    }

    override def collideWith(colony: Colony): Boolean = ???

  }

}
