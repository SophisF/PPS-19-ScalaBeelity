package scala.model.environment

import scala.model.environment.property.Variation.GenericVariation
import scala.model.environment.property.realization.TemperatureProperty
import scala.model.environment.property.source.ContinuousSource.ContinuousSourceImpl
import scala.model.environment.property.{FilterBuilder, Property, PropertyType, Variation}
import scala.model.environment.property.source.SeasonalSource.SeasonalSourceImpl
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
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
  def generateClimate(environmentWidth: Int, environmentHeight: Int, iterations: Int): Iterator[PropertySource[Property]] = {
    Iterator.continually(randomContinuousFilter(environmentWidth, environmentHeight, iterations)).take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)
  }

  //def generateSeason[T <: Property](): Iterable[PropertySource[T]] = PropertyType.values.toArray.map(SeasonalSourceImpl).toIterable

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def randomContinuousFilter(environmentWidth: Int, environmentHeight: Int, iterations: Int)
  : ContinuousSource[Property] = {
    val propertyType = PropertyType.Temperature
    //    val property = Property.range(propertyType)
    //    val values = if (Random.nextBoolean()) (property.maxValue, property.default)
    //    else (property.minValue, property.default)
    val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
    val filter = FilterBuilder.gaussianFunction3d(values._1, values._2, 70, 70)
    ContinuousSourceImpl(
      filter.mapValues(it => Variation.GenericVariation[TemperatureProperty](it.toInt).asInstanceOf[GenericVariation[Property]]),
      (_, _) => 100.asInstanceOf[TemperatureProperty.ValueType],
      Random.nextInt(environmentWidth), Random.nextInt(environmentHeight),
      filter.cols, filter.rows,
      0, iterations,
    )
  }
}
