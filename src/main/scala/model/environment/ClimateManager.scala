package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.{PropertyValue, properties}
import scala.model.environment.property.source.GlobalSource.SeasonalSource
import scala.model.environment.property.source.ZoneSource.Source
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.property.{Property, PropertyType, TimeDependentProperty}
import scala.util.Random.{between => randomDoubleIn, nextInt => randomInt}
import scala.utility.MathHelper.randomBoolean

/**
 * Manager for climate in environment.
 */
private[environment] object ClimateManager {
  private val filterXAxisDecrement = (environmentWidth: Int) => randomDoubleIn(.1, .2) * environmentWidth toInt
  private val filterYAxisDecrement = (environmentHeight: Int) => randomDoubleIn(.1, .2) * environmentHeight toInt

  /**
   * Generate local climate change in environment.
   *
   * @param environmentSize , dimension of environment
   * @param iterations      , number of iterations
   * @return an iterator of PropertySource
   */
  def generateLocalChanges(environmentSize: Size, iterations: Int): Iterator[PropertySource[TimeDependentProperty]] =
    Iterator.continually(PropertyType.random(_.isInstanceOf[TimeDependentProperty])).filter(_ nonEmpty)
      .map(_.get().asInstanceOf[TimeDependentProperty]).map(randomContinuousFilter(_, environmentSize, iterations))
      .takeWhile(_ => randomBoolean(5))

  /**
   * Generate season to apply at the environment.
   *
   * @return an iterator of PropertySource
   */
  def generateSeason(): Iterator[PropertySource[TimeDependentProperty]] = properties(_.isInstanceOf[TimeDependentProperty])
    .map(_.asInstanceOf[PropertyValue[TimeDependentProperty]]).map(seasonalChanges(_)).iterator

  /**
   * Create a random Continuos Filter.
   *
   * @param property        , property for filter
   * @param environmentSize , dimension of the environment
   * @param iterations      , number of iterations for source
   * @return a Continuos Source
   */
  private def randomContinuousFilter(property: TimeDependentProperty, environmentSize: Size, iterations: Int)
  : ContinuousSource[TimeDependentProperty] = ContinuousSource(
    randomInt(environmentSize width), randomInt(environmentSize height), iterations)(property.timedFilter(
    filterXAxisDecrement(environmentSize width), filterYAxisDecrement(environmentSize height), iterations)
    .asInstanceOf[DenseMatrix[TimeDependentProperty#TimedVariation]])

  /**
   * Create a random Instantaneous Filter.
   *
   * @param property        , property for filter
   * @param environmentSize , dimension of the environment
   * @return a Source for property
   */
  def randomInstantaneousFilter(property: Property, environmentSize: Size): Source[Property] =
    Source(
      property.generateFilter(filterXAxisDecrement(environmentSize width), filterYAxisDecrement(environmentSize height))
        .asInstanceOf[DenseMatrix[Property#Variation]],
      randomInt(environmentSize width), randomInt(environmentSize height)
    )

  /**
   * Create a seasonal changes for climate.
   *
   * @param property , property for filter
   * @return a Seasonal Source
   */
  private def seasonalChanges(property: TimeDependentProperty): SeasonalSource[TimeDependentProperty] =
    SeasonalSource(property.seasonalTrend)
}
