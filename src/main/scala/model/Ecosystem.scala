package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.bee.{Colony, Queen}
import scala.model.bees.genotype.Genotype
import scala.model.environment.EnvironmentManager.evolution
import scala.model.environment.{Cell, EnvironmentManager}
import scala.util.Random
import scala.utility.Point

class Ecosystem(nColonies: Int, width: Int, height: Int) {

  var environmentManager: EnvironmentManager = EnvironmentManager(width, height)
  var colonies: List[Colony] = (1 to nColonies).map(_ => createColony()).toList


  def update(): Unit = {
    this.environmentManager = evolution(environmentManager)
    this.colonies = Colony.manage(this.colonies.flatMap(_.update(Time.incrementValue)(environmentManager))
      .filter(_.isColonyAlive)).filter(_.isColonyAlive)
  }

  def createColony(position: Point = (Random.nextInt(environmentManager.environment.map.rows - 1), Random.nextInt(environmentManager.environment.map.cols - 1))): Colony = {
    val genotype = Genotype()
    val cell: Cell = environmentManager.environment.map(position.x, position.y)
    val queen: Queen = Queen(None, genotype, genotype expressItself, 0,
      cell.temperature.numericRepresentation(false), cell.pressure.numericRepresentation(false),
      cell.humidity.numericRepresentation(false), position, this.createColony)
    queen.colony
  }
}
