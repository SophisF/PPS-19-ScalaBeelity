package scala.model.environment

import breeze.linalg.DenseMatrix

import scala.model.environment.matrix.Size
import scala.model.environment.property.{Property, PropertyType, TimedProperty}
import scala.model.environment.property.source.{ContinuousSource, PropertySource}
import scala.model.environment.time.Time
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
  def generateClimate(environmentSize: Size, iterations: Int): Iterator[PropertySource[TimedProperty]] =
    Iterator.continually(randomContinuousFilter(environmentSize, iterations)).take(7)
    //.takeWhile(_ => Random.nextInt(TimeInterval) == Time.time % TimeInterval)

  //def generateSeason[T <: Property](): Iterable[PropertySource[T]] = PropertyType.values.toArray.map(SeasonalSourceImpl).toIterable

  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @return
   */
  private def randomContinuousFilter(environmentSize: Size, iterations: Int): ContinuousSource[TimedProperty] = {
    val properties = PropertyType.properties.filter(_.property.isInstanceOf[TimedProperty])
    val property = properties.toIndexedSeq(Random.nextInt(properties.length))().asInstanceOf[TimedProperty]
    val filter = property.generateTimedFilter(70, 70, Time.now(), Time.delay(Time.now(), 100))

    new ContinuousSource(filter.asInstanceOf[DenseMatrix[TimedProperty#TimedVariation]],
      Random.nextInt(environmentSize.width), Random.nextInt(environmentSize.height), 5)
  }
}
