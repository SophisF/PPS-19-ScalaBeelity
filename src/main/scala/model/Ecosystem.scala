package scala.model

import model.bees.bee.ColonyManager

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.environment.EnvironmentManager
import scala.model.environment.matrix.Point
import scala.util.Random

object Ecosystem {

  var colonies: List[Colony] = List.empty

  val environment = EnvironmentManager(300, 300)

  def initialize(nColonies: Int): Unit = {
    List.range(0, nColonies).foreach(_ => {
      this.colonies = createQueen(Point(50, 50)) :: this.colonies
    })
  }


  def update(time: Int): Unit = {


    this.colonies = this.colonies.flatMap(_.update(time)(environment)).filter(_.isColonyAlive)

    this.colonies.foreach(e => println(e.bees.size))

    //println("manage")

    // this.colonies.foreach(e => println(e.position.x + " " + e.bees.size))

    this.colonies = ColonyManager.manage(colonies).filter(_.isColonyAlive)






    //Tutto si riassume in questo
    // this.colonies = ColonyManager.manage(this.colonies.map(_.update(time)(20)(1000)(50)).filter(_.isColonyAlive)).filter(_.isColonyAlive)

  }

  def createQueen(position: Point = (Random.nextInt(environment.environment.map.rows), Random.nextInt(environment.environment.map.cols))): Colony = {
    //println("new")
    val genotype = Genotype()
    val queen: Queen = Queen(None, genotype, Phenotype(Genotype.calculateExpression(genotype)), 0, 20, 1000, 50, position, this.createQueen)
    queen.colony
  }

}
