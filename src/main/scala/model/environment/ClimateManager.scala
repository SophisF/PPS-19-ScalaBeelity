package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.Time
import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.{PropertyType, TimeDependentProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.util.Random

object ClimateManager {

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
      .map(_.get.property.asInstanceOf[TimeDependentProperty])
      .map(randomContinuousFilter(_, environmentSize, iterations)).take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)

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
  : ContinuousSource[TimeDependentProperty] = new ContinuousSource[TimeDependentProperty](
    property.timedFilter(5, 5, Time.now(), Time delay iterations)
      .asInstanceOf[DenseMatrix[TimeDependentProperty#TimedVariation]],
    Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), iterations)

  private def seasonalChanges(property: TimeDependentProperty): SeasonalSource[TimeDependentProperty] =
    SeasonalSource(property.seasonalTrend)
}
