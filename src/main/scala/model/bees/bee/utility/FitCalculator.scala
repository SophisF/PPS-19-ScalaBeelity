package scala.model.bees.bee.utility

import scala.model.bees.phenotype.EnvironmentInformation.EnvironmentInformation
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
   * @param fitValue  the fit value.
   * @param parameter the parameter to whom apply the fit value.
   * @param applier   a strategy operation to apply.
   * @return the effective parameter.
   */

  def applyFitValue(fitValue: Double)(parameter: Int)(applier: (Double, Int) => Double): Int = applier(fitValue, parameter).toInt

  /**
   * Method to calculate the fit value, based on the environment parameters.
   *
   * @param phenotype              the phenotype ot the bee.
   * @param environmentInformation the information of the environment.
   * @return the aggregated fit value.
   */
  def calculateFitValue(phenotype: Phenotype)(environmentInformation: EnvironmentInformation)
                       (fitAggregator: Seq[Double] => Double)
  : Double = {

    fitAggregator(environmentInformation.characteristicMap.map(kv => this.defaultCalculator(kv._2)(phenotype expressionOf kv._1)).toSeq)
  }

  /**
   * Default strategy to calculate the fit value of a property.
   *
   * @param property the property of the environment.
   * @param range    the optimal range.
   * @return the fit value, based on much the property departs from the range.
   */
  private def defaultCalculator(property: Int)(range: Range): Double =
    if (property in range) 1.0
    else if (property < range) 1.0 - (range._1.toDouble - property.toDouble) / 100
    else 1.0 - (property.toDouble - range._2.toDouble) / 100
}
