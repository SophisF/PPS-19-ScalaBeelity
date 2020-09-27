package scala.model.prolog

import alice.tuprolog._

import scala.model.prolog.PrologEngine._
import scala.utility.Point

/**
 *  Object to solve the movement logic.
 */
object MovementLogic {

  /**
   * Method that exploits Prolog logic to find the new position of the colony.
   * @param cells a list of terms that represents the cells.
   * @param temperatureRange a term that represents the temperature range of the queen.
   * @param pressureRange a term that represents the pressure range of the queen.
   * @param humidityRange a term that represents the humidity range of the queen.
   * @return the best Point where the queen must move to.
   */
  def solveLogic(cells: Seq[Term], temperatureRange: String, pressureRange: String, humidityRange: String): Option[Point] = {
    val engine: Term => Iterable[SolveInfo] = mkPrologEngine(new Theory(getClass.getResource("/movement.pl").openStream()))
    val move = new Struct("move", cells, temperatureRange, pressureRange, humidityRange, new Var())
    val newCell = engine(move) map (extractTerm(_, 4))
    if(newCell.isEmpty){
      None
    }
    else {
      val position = new Struct("getPosition", newCell head, new Var(), new Var())
      Some ((engine(position) map (t => Point(extractTerm(t, 1), extractTerm(t, 2)))) head)
    }

  }
}
