package scala.model

import breeze.linalg.DenseMatrix

import scala.model.matrix._
import scala.model.property.PropertySource.{InstantaneousPropertySource, border, in}

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
  def +(filter: InstantaneousPropertySource): Environment = new Environment(map.mapPairs((pointXY, cell) =>
    pointXY match {
      case p if in(p._1, p._2, filter) => cell + filter.data(p._1 - border(filter)(Size.Left), p._2 - border(filter)(Size.Top))
      case _ => cell
    }))

  /*def +(filters: Iterable[Filter[Int]]): Environment = modify(filters.map(linearize)
    .reduce((_1, _2) => _1.appendedAll(_2)).sortWith(Point.compare).toSeq)

  def modify(variations: Seq[CellVariation[Int]]): Environment = new Environment(
    map.mapPairs((pointXY, cell) => variations.take(1) match {
      case seq if seq.isEmpty => cell
      case seq => cell + seq.find(==(_, Point.toPoint(pointXY)))
    }))*/
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
}