package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.PropertyType.PropertyValue
import scala.model.environment.property.{Property, PropertyType, TimedProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource, SeasonalSource}
import scala.model.environment.time.Time
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
  def generateLocalChanges(environmentSize: Size, iterations: Int): Iterator[PropertySource[TimedProperty]] =
    Iterator.continually(randomContinuousFilter(environmentSize, iterations)).take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)

  def generateSeason(): Iterator[PropertySource[TimedProperty]] = PropertyType.properties(_.isInstanceOf[TimedProperty])
    .map(_.asInstanceOf[PropertyValue[TimedProperty]]).map(it => seasonalChanges(it())).iterator

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter(environmentSize: Size, iterations: Int): ContinuousSource[TimedProperty] = {
    val property = PropertyType.random(_.isInstanceOf[TimedProperty]).map(_().asInstanceOf[TimedProperty])
    val filter = property.map(_.generateTimedFilter(70, 70, Time.now(), Time.delay(Time.now(), 100)))

    new ContinuousSource(filter.get.asInstanceOf[DenseMatrix[TimedProperty#TimedVariation]],
      Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), 5)
  }

  private def seasonalChanges(property: TimedProperty): SeasonalSource[TimedProperty] = {
    new SeasonalSource(property.seasonalTrend(Time.now()))
  }
}
