package scala.controller

import scala.model.environment.property.PropertySource.SeasonalPropertySource
import scala.model.environment.property.PropertyVariation.Variation
import scala.model.environment.property.ZonePropertySource.ContinuousZonePropertySource
import scala.model.environment.property.{FilterBuilder, Property, PropertySource, ZonePropertySource}
import scala.util.Random

object GeneratorClimateChange {

  private val TimeInterval = 1


  /**
   * Timed generator for non periodically climate changes.
   *
   * @param environmentWidth ,
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def generateClimate(environmentWidth: Int, environmentHeight: Int, iterations: Int): Iterator[PropertySource] = {
    Iterator.continually(randomContinuousFilter(environmentWidth, environmentHeight, iterations))
      .take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)
  }

  def generateSeason(): Iterable[PropertySource] =
    Property.values.toArray.map(SeasonalPropertySource).toIterable

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def randomContinuousFilter(environmentWidth: Int, environmentHeight: Int, iterations: Int)
  : ContinuousZonePropertySource = {
    val propertyType = Property.randomPropertyType()
    //    val property = Property.range(propertyType)
    //    val values = if (Random.nextBoolean()) (property.maxValue, property.default)
    //    else (property.minValue, property.default)
    val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
    val filter = FilterBuilder.gaussianFunction3d(500, 1, 20, 20)
    ZonePropertySource(
      Random.nextInt(environmentWidth), Random.nextInt(environmentHeight),
      filter.cols, filter.rows,
      0, iterations, filter.mapValues(it => Variation(propertyType, it.toInt))
    )
  }
}
