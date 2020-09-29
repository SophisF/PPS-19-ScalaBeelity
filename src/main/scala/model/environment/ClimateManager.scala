package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.properties
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.ZoneSource.Source
import scala.model.environment.property.{Property, PropertyType, TimedProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.property.utils.{SeasonalBehaviour, FilterGenerator, TimedFilterGenerator}
import scala.util.Random.{between => randomDoubleIn, nextInt => randomInt}
import scala.utility.MathHelper.randomBoolean

object ClimateManager {
  private val filterXAxisDecrement = (environmentWidth: Int) => randomDoubleIn(.1, .2) * environmentWidth toInt
  private val filterYAxisDecrement = (environmentHeight: Int) => randomDoubleIn(.1, .2) * environmentHeight toInt

  /**
   * Timed generator for non periodically climate changes.
   *
   * @param environmentWidth ,
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def generateLocalChanges(environmentSize: Size, iterations: Int): Iterator[PropertySource[TimedProperty]] =
    Iterator.continually(PropertyType.random[TimedFilterGenerator]).filter(_ nonEmpty)
      .map(_.get).map(randomContinuousFilter(_, environmentSize, iterations))
      .takeWhile(_ => randomBoolean(5))

  def generateSeason(): Iterator[PropertySource[TimedProperty]] = properties[SeasonalBehaviour].map(seasonalChanges)
    .iterator

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter(property: TimedFilterGenerator, environmentSize: Size, iterations: Int)
  : ContinuousSource[TimedProperty] = ContinuousSource(
    randomInt(environmentSize width), randomInt(environmentSize height), iterations)(property.timedFilter(
    filterXAxisDecrement(environmentSize width), filterYAxisDecrement(environmentSize height), iterations)
    .asInstanceOf[DenseMatrix[TimedProperty#TimedVariation]])

  def randomInstantaneousFilter(property: FilterGenerator, environmentSize: Size): Source[Property] =
    Source(
      property.generateFilter(filterXAxisDecrement(environmentSize width), filterYAxisDecrement(environmentSize height))
        .asInstanceOf[DenseMatrix[Property#Variation]],
      randomInt(environmentSize width), randomInt(environmentSize height)
    )

  private def seasonalChanges(property: SeasonalBehaviour): SeasonalSource[TimedProperty] =
    SeasonalSource(property.seasonalTrend)
}
