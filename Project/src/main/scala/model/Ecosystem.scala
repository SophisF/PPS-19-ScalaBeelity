package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.Queen.{Queen, QueenImpl}
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.GenotypeImpl
import scala.model.bees.phenotype.Phenotype.PhenotypeImpl
import scala.model.environment.{Cell, Environment}

object Ecosystem {

  val width: Int = 1000
  val height: Int = 1000

  var colonies: Set[Colony] = Set.empty

  val environment: Environment = Environment((width, height), new Cell(20, 50, 1000))

  def createQueen(): Unit = {
    val genotype = GenotypeImpl()
   val queen: Queen = QueenImpl(None, genotype, PhenotypeImpl(Genotype.calculateExpression(genotype)), 0, 20, 50, 1000)
    colonies = colonies + queen.colony
  }

}
