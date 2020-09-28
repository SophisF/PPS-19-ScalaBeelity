package scala.model.bees.bee.utility

import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.utility.PimpInt._
import scala.utility.TypeUtilities.Range

/**
 * Object used to calculate the fit value of the phenotype's characteristics, based on the environment parameters.
 */
object FitCalculator {

  /**
   * Method to apply a fit value to a parameter, based on the operation passed.
   *
   * @param fitValue   the fit value.
   * @param parameter  the parameter to whom apply the fit value.
   * @param applier a strategy operation to apply.
   * @return the effective parameter.
   */

  def applyFitValue(fitValue: Double)(parameter: Int)(applier: (Double, Int) => Double): Int = applier(fitValue, parameter).toInt

  /**
   * Method to calculate the fit value, based on the environment parameters.
   *
   * @param phenotype   the phenotype ot the bee.
   * @param averageTemperature the average temperature of the environment where the bee's colony is.
   * @param averagePressure    the average pressure of the environment where the bee's colony is.
   * @param averageHumidity    the average humidity of the environment where the bee's colony is.
   * @param fitAggregator   a strategy to calculate the fitValue.
   * @return the aggregated fit value.
   */
  def calculateFitValue(phenotype: Phenotype)(averageTemperature: Int)(averagePressure: Int)(averageHumidity: Int)
                       (fitAggregator: Seq[Double] => Double)
  : Double = {

    val tFit: Double = this.defaultCalculator(averageTemperature)(phenotype
       expressionOf CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY)
    val pFit: Double = this.defaultCalculator(averagePressure)(phenotype
      expressionOf CharacteristicTaxonomy.PRESSURE_COMPATIBILITY)
    val hFit: Double = this.defaultCalculator(averageHumidity)(phenotype
      expressionOf CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY)

    fitAggregator(Seq(tFit, pFit, hFit))
  }

  /**
   * Default strategy to calculate the fit value of a property.
   * @param property the property of the environment.
   * @param range the optimal range.
   * @return the fit value, based on much the property departs from the range.
   */
  private def defaultCalculator(property: Int)(range: Range): Double =
    if (property in range) 1.0
    else if (property < range) 1.0 - (range._1.toDouble - property.toDouble) / 100
    else 1.0 - (property.toDouble - range._2.toDouble) / 100
}
