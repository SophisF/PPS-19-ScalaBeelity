package model.prolog

import java.io.FileInputStream

import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory, Var}

import scala.model.environment.EnvironmentManager

object MovementLogic {

  def extractTerm(solveInfo:SolveInfo, i:Integer): Term =
    solveInfo.getSolution.asInstanceOf[Struct].getArg(i).getTerm

  def extractTerm(solveInfo:SolveInfo, s:String): Term =
    solveInfo.getTerm(s)


  implicit def stringToTerm(s: String): Term = Term.createTerm(s)
  implicit def seqToTerm[T](s: Seq[T]): Term = s.mkString("[",",","]")
  implicit def stringToTheory[T](s: String): Theory = new Theory(s)

  def mkPrologEngine(theory: Theory): Term => Iterable[SolveInfo] = {
    val engine = new Prolog
    engine.setTheory(theory)

    goal => new Iterable[SolveInfo]{

      override def iterator = new Iterator[SolveInfo]{
        var solution: Option[SolveInfo] = Some(engine.solve(goal))

        override def hasNext = solution.isDefined &&
          (solution.get.isSuccess || solution.get.hasOpenAlternatives)

        override def next() =
          try solution.get
          finally solution = if (solution.get.hasOpenAlternatives) Some(engine.solveNext()) else None
      }
    }
  }

  def solveWithSuccess(engine: Term => Iterable[SolveInfo], goal: Term): Boolean =
    engine(goal).map(_.isSuccess).headOption == Some(true)

  def solveOneAndGetTerm(engine: Term => Iterable[SolveInfo], goal: Term, term: String): Term =
    engine(goal).headOption map (extractTerm(_,term)) get
}


object TryScala2P extends App {
  import MovementLogic._

  val environment = EnvironmentManager(1000, 1000)

  val engine: Term => Iterable[SolveInfo] = mkPrologEngine(new Theory(new FileInputStream("src/main/scala/model/prolog/prolog.pl")))

  val rt = "range(20.0, 25.0)"
  val rp = "range(980.0, 995.0)"
  val rh = "range(50.0, 80.0)"

  val position = (100, 100)
  val dimension = 10

  val cells = for {
    i <- position._1 - dimension to position._1 + dimension
    j <- position._2 - dimension to position._2 + dimension
    cell = environment.environment.map.valueAt(i, j)

  } yield s"cell(${cell.temperature.toDouble}, ${cell.pressure.toDouble}, ${cell.humidity.toDouble}, position($i, $j))"

  println(seqToTerm(cells))




  //engine() foreach (println(_))
  // permutation([1,2,3],[1,2,3]) ... permutation([1,2,3],[3,2,1])

  val input = new Struct("maxFit", "50.0", "range(20.0, 25.0)", new Var())
  engine(input) map (extractTerm(_,2)) foreach (println(_))
 // engine(input) map (extractTerm(_,1)) take 100 foreach (println(_))
  // [1,2,3,4,..,20] ... [1,2,..,15,20,16,18,19,17]


}


