package scala.model.bees.bee

import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.utility.PimpInt._

/**
 * Object used to calculate the fit value of the phenotype's characteristics, based on the environment parameters.
 */
object Fitter {

  type Range = (Int, Int)

  /**
   * Method to apply a fit value to a parameter, based on the operation passed.
   * @param fitValue the fit value.
   * @param parameter the parameter to whom apply the fit value.
   * @param operation the operation to apply.
   * @return the effective parameter.
   */
  def applyFitValue(fitValue: Double)(parameter: Int)(operation: (Double, Int) => Double): Int = operation(fitValue, parameter).toInt

  /**
   * Method to calculate the fit value, based on the environment parameters.
   * @param phenotype the phenotype ot the bee.
   * @param temperature the temperature of the environment.
   * @param pressure the pressure of the environment.
   * @param humidity the humidity of the environment.
   * @return the fit value.
   */
  def calculateFitValue(phenotype: Phenotype)(temperature: Int)(pressure: Int)(humidity: Int): Double = {
    val tFit: Double = this.calculateFit(temperature)(phenotype.temperatureCompatibility.expression)
    val pFit: Double = this.calculateFit(pressure)(phenotype.pressureCompatibility.expression)
    val hFit: Double = this.calculateFit(humidity)(phenotype.humidityCompatibility.expression)

    (tFit + pFit + hFit) / 3
  }

  /**
   * Defines how the fit value must be computed.
   * @param property the property of the environment.
   * @param range the expression of the property in the phenotype.
   * @return the fit value of the property.
   */
  private def calculateFit(property: Int)(range: Range): Double = {
    if(property in range) 1.0 else if(property < range) 1.0 - (range._1.toDouble - property.toDouble) / 100
    else 1.0 - (property.toDouble - range._2.toDouble) / 100
  }

}
