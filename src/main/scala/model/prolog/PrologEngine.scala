package scala.model.prolog


import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

import scala.model.environment.Cell
import scala.utility.Point

/**
 * Utility object to manage Prolog engine.
 */
object PrologEngine {

  /**
   * Method to build a cell term, given an environmental cell and its position.
   * @param cell the environmental cell.
   * @param position the position of the cell.
   * @return a cell as a term.
   */
  def buildCellTerm(cell: Cell, position: Point): Term = {
    s"cell(${cell.temperature}, ${cell.pressure}, ${cell.humidity}, position(${position.x}, ${position.y}))"
  }

  /**
   * Method to extract a term from a solved theory.
   * @param solveInfo a solution of the theory.
   * @param i the position of the term in the solution.
   * @return the correct term.
   */
  def extractTerm(solveInfo:SolveInfo, i:Integer): Term =
    solveInfo.getSolution.asInstanceOf[Struct].getArg(i).getTerm

  /**
   * Method to extract a term from a solved theory.
   * @param solveInfo a solution of the theory.
   * @param s the name of the term.
   * @return the correct term.
   */
  def extractTerm(solveInfo:SolveInfo, s:String): Term =
    solveInfo.getTerm(s)

  /**
   * Implicit method to convert a string to a Prolog term.
   * @param s the string to convert.
   * @return the Prolog term.
   */
  implicit def stringToTerm(s: String): Term = Term.createTerm(s)

  /**
   * Implicit method to convert a generic sequence to a Prolog list of term.
   * @param s the sequence to convert.
   * @tparam T the type of the sequence's elements.
   * @return the Prolog list of term.
   */
  implicit def seqToTerm[T](s: Seq[T]): Term = s.mkString("[",",","]")

  /**
   * Implicit method to convert a string to a Prolog theory.
   * @param s the string to convert.
   * @return the Prolog theory.
   */
  implicit def stringToTheory(s: String): Theory = new Theory(s)

  /**
   * Method to convert a Prolog term to a integer value.
   * @param t the Prolog term.
   * @return the integer value of the term.
   */
  implicit def termToInt(t: Term): Int = t.toString.toInt

  /**
   * Method to solve a Prolog theory.
   * @param theory the theory to solve.
   * @return an iterable of solutions.
   */
  def mkPrologEngine(theory: Theory): Term => Iterable[SolveInfo] = {
    val engine = new Prolog
    engine.setTheory(theory)

    goal => new Iterable[SolveInfo]{

      override def iterator: Iterator[SolveInfo] = new Iterator[SolveInfo]{
        var solution: Option[SolveInfo] = Some(engine.solve(goal))

        override def hasNext: Boolean = solution.isDefined &&
          (solution.get.isSuccess || solution.get.hasOpenAlternatives)

        override def next(): SolveInfo =
          try solution.get
          finally solution = if (solution.get.hasOpenAlternatives) Some(engine.solveNext()) else None
      }
    }
  }
}

