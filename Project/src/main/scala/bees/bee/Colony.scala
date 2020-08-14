package bees.bee

import bees.bee.Bee.Bee
import bees.bee.Queen.Queen

import scala.collection.immutable.HashSet

object Colony {
  trait Colony {
    def dimension: Int = getBees.size
    def getBees: Set[Bee]
    def ++(bees: Bee*): Unit
    def +(bee: Bee): Unit
    def isColonyAlive: Boolean
    def isQueenAlive: Boolean
    def update(): Unit
  }

  case class ColonyImpl(private val queen: Queen) extends Colony{
    private var bees: Set[Bee] = HashSet(queen)

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
    override def update(): Unit = ???
  }

}
