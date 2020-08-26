package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen
import scala.model.bees.bee.Queen.Queen
import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.environment.{Cell, Environment}

object Ecosystem {

  val width: Int = 1000
  val height: Int = 1000

  var colonies: Set[Colony] = Set.empty

  val environment: Environment = Environment((width, height), new Cell(20, 50, 1000))

  def initialize(nColonies: Int): Unit = {
    List.range(0, nColonies).foreach(_ => this.createQueen())
  }

  def createQueen(): Unit = {
    val genotype = Genotype()
   val queen: Queen = Queen(None, genotype, Phenotype(Genotype.calculateExpression(genotype)), 0, 20, 50, 1000)
    colonies = colonies + queen.colony
  }

}
