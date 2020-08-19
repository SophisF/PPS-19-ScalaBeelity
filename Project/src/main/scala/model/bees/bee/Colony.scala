package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.ColonyArea.{ColonyArea, ColonyAreaImpl, Ends}
import scala.model.bees.bee.Queen.Queen

import scala.collection.immutable.HashSet

/**
 * Object that represents colony
 */
object Colony {

  /**
   * Trait that represents colony
   */
  trait Colony {
    def getBees: Set[Bee]
    def getPosition: Ends
    def ++(bees: Bee*): Unit
    def +(bee: Bee): Unit
    def isColonyAlive: Boolean
    def isQueenAlive: Boolean
    def update(): Unit
    def collideWith(colony: Colony): Boolean
  }

  /**
   * Class that represents colony
   * @param queen the only queen of the colony
   */
  case class ColonyImpl(private val queen: Queen) extends Colony{
    private var bees: Set[Bee] = HashSet(queen)
    private var dimension: Int = 0
    private val limitBeesForCell: Int = 10
    private var  area: ColonyArea = ColonyAreaImpl(queen.position, dimension)

    override def getBees: Set[Bee] = this.bees

    override def ++(bees: Bee*): Unit = this.bees = this.bees ++ bees

    override def +(bee: Bee): Unit = this.bees = this.bees + bee

    override def isColonyAlive: Boolean = this.isQueenAlive && this.bees.exists {
      case _: Queen => false
      case _ => true
    }

    override def isQueenAlive: Boolean = this.bees.exists {
      case _:Queen => true
      case _ => false
    }

    //based on environment parameters will call the difference update.
    //check problems with queen position
    override def update(): Unit = {
      if (bees.size > area*limitBeesForCell) {dimension+=1; area = ColonyAreaImpl(queen.position, dimension)}
      else if (bees.size < ColonyAreaImpl(queen.position, dimension-1)*limitBeesForCell) {dimension-=1; area = ColonyAreaImpl(queen.position, dimension)}
    }

    override def getPosition: Ends = area.getEnds()

    override def collideWith(colony: Colony): Boolean = ???
  }

}
