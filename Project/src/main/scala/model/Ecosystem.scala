package scala.model

import scala.model.bees.bee.Colony.Colony
import scala.model.environment.{Cell, Environment}

object Ecosystem {

  val width: Int = 1000
  val height: Int = 1000

  var colonies: Set[Colony] = Set.empty

  val environment: Environment = Environment((width, height), new Cell(20, 50, 1000))

  def createQueen(): Unit = {
   // val queen: Queen = QueenImpl(None)
    //colonies = colonies + queen.colony
  }

}
