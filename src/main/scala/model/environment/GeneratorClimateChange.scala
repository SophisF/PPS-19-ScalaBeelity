package scala.model.environment

import scala.model.environment.matrix.Size
import scala.model.environment.property.realization.HumidityPropertyHelper
import scala.model.environment.property.Property
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.util.Random

object GeneratorClimateChange {

  /**
   * Timed generator for non periodically climate changes.
   *
   * @param environmentWidth ,
   * @param environmentHeight
   * @param iterations
   * @return
   */
  def generateClimate(environmentSize: Size, iterations: Int): Iterator[PropertySource[Property]] =
    Iterator.continually[PropertySource[Property]](randomContinuousFilter(environmentSize, iterations)).take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)

  //def generateSeason[T <: Property](): Iterable[PropertySource[T]] = PropertyType.values.toArray.map(SeasonalSourceImpl).toIterable

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter[T <: Property](environmentSize: Size, iterations: Int): ContinuousSource[T] = {
    val filter = HumidityPropertyHelper.generateContinuousFilter(70, 70, iterations)

    ContinuousSource(filter, Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), filter.cols,
      filter.rows, 0, iterations)((value, percentage) => value * percentage / 100).asInstanceOf[ContinuousSource[T]]
  }
}
