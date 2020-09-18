package scala.model

import model.bees.bee.ColonyManager

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.environment.EnvironmentManager.evolution
import scala.model.environment.{Cell, EnvironmentManager}
import scala.utility.Point
import scala.util.Random

class Ecosystem(nColonies: Int, width: Int, height: Int) {


  var environmentManager: EnvironmentManager.EnvironmentManager = EnvironmentManager(width, height)
  var colonies: List[Colony] = (0 to nColonies).map(_ => createQueen()).toList

  def update(): Unit = {
    this.environmentManager = evolution(environmentManager)
    this.colonies = ColonyManager.manage(this.colonies.flatMap(_.update(Time.incrementValue)(environmentManager))
      .filter(_.isColonyAlive)).filter(_.isColonyAlive)
  }

  def createQueen(position: Point = (Random.nextInt(environmentManager.environment.map.rows - 1), Random.nextInt(environmentManager.environment.map.cols - 1))): Colony = {
    val genotype = Genotype()
    val cell: Cell = environmentManager.environment.map(position.x, position.y)
    val queen: Queen = Queen(None, genotype, Phenotype(Genotype.calculateExpression(genotype)), 0,
      cell.temperature, cell.pressure, cell.humidity, position, this.createQueen)
    queen.colony
  }

}
