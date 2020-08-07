package scala.model

import alice.tuprolog.{Prolog, Theory}
import breeze.linalg.DenseMatrix

import scala.io.Source
import scala.model.Environment.engine
import scala.model.property.Filter._

/**
 * A first scratch of the environment class.
 *
 * @param map of the environment. Represented as a grid.
 *
 * @author Paolo Baldini
 */
case class Environment private (map: DenseMatrix[Cell]) {

  /**
   * Apply a filter to the environment
   *
   * @param filter to apply
   * @return modified environment
   */
  def +(filter: Filter[Int]): Environment = new Environment(map.mapPairs((pointXY, cell) => pointXY match {
      case (x, y) if x >= leftBorder(filter) && x < rightBorder(filter) && y >= topBorder(filter)
        && y < bottomBorder(filter) => cell + filter.data(x - leftBorder(filter), y - topBorder(filter))
      case _ => cell
    }))

  /**
   * A version of the previous method that try to use Prolog.
   * TODO it probably don't look fast because it run on a single thread (differently from breeze).
   *
   * @param filter to apply
   * @return modified environment
   */
  def `+prolog`(filter: Filter[Int]): Environment = {
    val mapMatrix = map.data.grouped(map.cols).map(_.map(_.temperature).mkString("[", ",", "]")).mkString("[", ",", "]")
    val filterMatrix = filter.data.data.grouped(map.cols).map(_.map(_.value).mkString("[", ",", "]")).mkString("[", ",", "]")
    val query = s"apply($mapMatrix, $filterMatrix, ${filter.center._1 - filter.size._1}, ${filter.center._2 - filter.size._2}, O)."
    val res = engine.solve(query)
    this
  }
}
object Environment {
  type Matrix[T] = Array[Array[T]]
  type SizeWH = (Int, Int)

  /**
   * Create a new environment
   *
   * @param size of the grid/map
   * @param defaultCell initial value for the cells of the map
   * @return the environment
   */
  def apply(size: SizeWH, defaultCell: Cell) = new Environment(DenseMatrix.create(size._1, size._2, Iterator
    .continually(defaultCell).take(size._1 * size._2).toArray))

  // TODO chose one of the following and delete the other

  def `+scala`(environment: Environment, filter: Filter[Int]): Environment = environment.+(filter)

  val engine = new Prolog()
  val externalTheory = new Theory(Source.fromFile("Project/src/main/prolog/filter_applier.pl").getLines().mkString("", "\n", ""))
  engine.addTheory(externalTheory)
  def `+prolog`(environment: Environment, filter: Filter[Int]): Environment = environment.`+prolog`(filter)
}