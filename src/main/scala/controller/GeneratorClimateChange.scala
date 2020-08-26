package scala.controller

import scala.model.Time
import scala.model.environment.property.Property.Pressure
import scala.model.environment.property.PropertyVariation.Variation
import scala.model.environment.property.{FilterBuilder, PropertySource, ZonePropertySource}
import scala.model.environment.property.ZonePropertySource.ContinuousZonePropertySource
import scala.util.Random

object GeneratorClimateChange {

  private val TimeInterval = 5


  /**
   * Timed generator for non periodically climate changes.
   *
   * @param environmentWidth,
   * @param environmentHeight
   * @param iterations
   * @param quantity
   * @return
   */
  def generateClimate(environmentWidth: Int, environmentHeight: Int, iterations: Int, quantity: Int): Option[Iterable[PropertySource]] =
    if (Random.nextInt(TimeInterval) == Time.time % TimeInterval)
      Some(randomContinuousFilters(environmentWidth, environmentHeight, iterations, quantity))
    else None

  //def generateSeason


  /**
   *
   * @param environmentWidth
   * @param environmentHeight
   * @param iterations
   * @param quantity
   * @return
   */
  def randomContinuousFilters(environmentWidth: Int, environmentHeight: Int, iterations: Int, quantity: Int)
  : Iterable[ContinuousZonePropertySource] = (0 until quantity) map (_ => {
    val values = if (Random.nextBoolean()) (50, 1) else (-50, -1)
    val filter = FilterBuilder.gaussianFunction3d(values._1, values._2, 70, 70)

    ZonePropertySource(
      Random.nextInt(environmentWidth), Random.nextInt(environmentHeight),
      filter.cols, filter.rows,
      0, iterations, filter.mapValues(it => Variation(Pressure, it.toInt))
    )
  })
}
