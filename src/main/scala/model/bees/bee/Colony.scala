package scala.model.bees.bee

import scala.model.bees.bee.Bee.Bee
import scala.model.bees.bee.Queen.Queen
import scala.model.environment.matrix.Point

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
    val position: Point
    val area: Int

    val isColonyAlive: Boolean
    val isQueenAlive: Boolean
    def update(time: Int): Unit
    def collideWith(colony: Colony): Boolean
  }


  case class ColonyImpl(override val queen: Queen, override val bees: Seq[Bee] = List.empty) extends Colony{

    //TODO ovveriding of equals


    //Da mettere in update
    //private var averageGenotype = EvolutionManager.calculateAverageGenotype(this._bees)
    //private var averagePhenotype = EvolutionManager.calculateAveragePhenotype(this.averageGenotype)


    override lazy val position: Point = this.queen.position

    override lazy val dimension: Int = (math.ceil(math.sqrt(this.bees.size.toDouble / limitBeesForCell)) / 2.0).toInt

    override lazy val area: Int = (this.dimension * 2 + 1) ^ 2

    override lazy val isColonyAlive: Boolean = this.isQueenAlive && this.bees.exists(_.isAlive)

    override lazy val isQueenAlive: Boolean = this.queen.isAlive

    override def update(time: Int): Unit = {



    }

    override def collideWith(colony: Colony): Boolean = false

  }

}
