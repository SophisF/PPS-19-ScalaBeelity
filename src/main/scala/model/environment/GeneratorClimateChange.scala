package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.realization.{HumidityPropertyHelper, PressurePropertyHelper, TemperaturePropertyHelper}
import scala.model.environment.property.{Property, PropertyType}
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
  private def randomContinuousFilter(environmentSize: Size, iterations: Int): ContinuousSource[Property] = {
    val filter = PropertyType.random().helper.generateInstantaneousFilter(70,70)

    ContinuousSource(filter.asInstanceOf[DenseMatrix[Property#Variation]], Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), filter.cols,
      filter.rows, 0, iterations)
  }
}
