package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.{PropertyType, TimeDependentProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.utility.SequenceHelper.RichSequence
import scala.util.Random

object ClimateManager {
  private def newFilter() = (0 until 20).random().get == 0 // TODO maybe move outside

  /**
   * Timed generator for non periodically climate changes.
   *
   * @param environmentWidth ,
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def generateLocalChanges(environmentSize: Size, iterations: Int): Iterator[PropertySource[TimeDependentProperty]] =
    Iterator.continually(PropertyType.random(_.isInstanceOf[TimeDependentProperty])).filter(_ nonEmpty)
      .map(_.get().asInstanceOf[TimeDependentProperty]).map(randomContinuousFilter(_, environmentSize, iterations))
      .takeWhile(_ => newFilter())

  def generateSeason(): Iterator[PropertySource[TimeDependentProperty]] = PropertyType.properties(_.isInstanceOf[TimeDependentProperty])
    .map(_.asInstanceOf[PropertyValue[TimeDependentProperty]]).map(it => seasonalChanges(it())).iterator

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter(property: TimeDependentProperty, environmentSize: Size, iterations: Int)
  : ContinuousSource[TimeDependentProperty] = new ContinuousSource(property.timedFilter(5, 5, iterations)
      .asInstanceOf[DenseMatrix[TimeDependentProperty#TimedVariation]],
    Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), iterations)

  private def seasonalChanges(property: TimeDependentProperty): SeasonalSource[TimeDependentProperty] =
    SeasonalSource(property.seasonalTrend)
}
