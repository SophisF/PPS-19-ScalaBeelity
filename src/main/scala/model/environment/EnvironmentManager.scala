package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.ClimateManager.{generateLocalChanges, generateSeason, randomInstantaneousFilter}
import scala.model.environment.adapter.Cell
import scala.model.environment.adapter.Cell.cellFromPrivateOne
import scala.model.environment.matrix.Size
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType.properties
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.property.utils.FilterGenerator
import scala.model.environment.time.Timed.isEnded
import scala.util.Random
import scala.utility.Point

/**
 * Manager of the environment, that control its evolution.
 *
 * @param environment     , the environment to manage.
 * @param propertySources , the property to at the environment.
 */
private[model] case class EnvironmentManager(
  private val environment: Environment,
  propertySources: PropertySource[Property]*
) {

  /**
   * Accessor of matrix of cells.
   *
   * @return matrix of cells.
   */
  def cells(): DenseMatrix[Cell] = environment.map.mapValues(cellFromPrivateOne)

  /**
   * Width of environment.
   *
   * @return width
   */
  def width: Int = environment.width

  /**
   * Height of environment.
   *
   * @return height
   */
  def height: Int = environment.height

  /**
   * Method to calculate the indexes of cells in a range, inside the environment.
   * @param range1 the first range.
   * @param range2 the second range.
   * @return a sequence of indexes.
   */
  def indexInRange(range1: (Int, Int), range2: (Int, Int)): Seq[(Int, Int)] = for {
    i <- range1._1 to range1._2
    j <- range2._1 to range2._2
    if i > 0 && i < environment.map.rows &&
      j > 0 && j < environment.map.cols
  } yield (i, j)

  /**
   * Method to calculate a point in proximity of an interval of cells.
   * @param range1 the first range, represents the x range.
   * @param range2 the second range, represents the y range.
   * @return a new Point near the interval.
   */
  def proximityOf(range1: (Int, Int), range2: (Int, Int)): Point = {
    val cells = indexInRange(range1, range2)
    val index = Random.nextInt(cells.size)
    Point(cells(index)._1, cells(index)._2)
  }

}

private[model] object EnvironmentManager {

  private val minRandom: Int = 10
  private val maxRandom: Int = 20
  private val iterationsFilter: Int = 200

  /**
   * Apply function.
   *
   * @param width  of environment.
   * @param height of environment.
   * @return an environment manager.
   */
  def apply[T <: Property](width: Int, height: Int): EnvironmentManager = {
    val seasonalEnvironment = generateSeason().foldLeft(EnvironmentManager(Environment(width, height)))(addSource)
    val filters = for {
      property <- properties[FilterGenerator]
      _ <- 0 to Random.between(minRandom, maxRandom)
    } yield randomInstantaneousFilter(property, Size(width, height))
    evolution(filters.foldLeft(seasonalEnvironment)(addSource))
  }

  /**
   * Apply property source at the environment and control property source.
   *
   * @param manager , environment manager to evolute.
   * @return environment manager evoluted.
   *
   */
  def evolution(manager: EnvironmentManager): EnvironmentManager =

    generateLocalChanges((manager.environment.map.cols, manager.environment.map.rows), iterationsFilter)
      .foldLeft(EnvironmentManager(
        manager.propertySources.foldLeft(manager.environment)(Environment.apply),
        manager.propertySources.filter {
          case p: ContinuousSource[_] => !isEnded(p)
          case p => p.isInstanceOf[SeasonalSource[_]]
        }: _*
      ))(addSource)

  def addSource[T <: Property](manager: EnvironmentManager, source: PropertySource[T]): EnvironmentManager =
    EnvironmentManager(manager.environment, manager.propertySources :+ source.asInstanceOf[PropertySource[Property]]:_*)
}
