package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.statistic.StatisticEnvironment.PropertyType
import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.ZoneSource.Source
import scala.model.environment.property.{Property, PropertyType, TimeDependentProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.util.Random
import scala.utility.MathHelper.randomBoolean

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
      .map(_.get().asInstanceOf[TimeDependentProperty]).map(randomContinuousFilter(_, environmentSize, iterations))
      .takeWhile(_ => randomBoolean(5))

  def generateSeason(): Iterator[PropertySource[TimeDependentProperty]] = PropertyType.properties(_.isInstanceOf[TimeDependentProperty])
    .map(_.asInstanceOf[PropertyValue[TimeDependentProperty]]).map(seasonalChanges(_)).iterator

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter(property: TimeDependentProperty, environmentSize: Size, iterations: Int)
  : ContinuousSource[TimeDependentProperty] = new ContinuousSource(property.timedFilter(10, 10, iterations)
    .asInstanceOf[DenseMatrix[TimeDependentProperty#TimedVariation]],
    Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), iterations)

  def randomInstantaneousFilter(property: PropertyType, environmentSize: Size): Source[Property] =
    Source(property.generateFilter(10, 10).asInstanceOf[DenseMatrix[Property#Variation]],
      Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height))

  private def seasonalChanges(property: TimeDependentProperty): SeasonalSource[TimeDependentProperty] =
    SeasonalSource(property.seasonalTrend)
}
