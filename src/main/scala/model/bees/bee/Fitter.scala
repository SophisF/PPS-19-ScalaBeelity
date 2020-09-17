package scala.model.bees.bee

import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.utility.PimpInt._

/**
 * Object used to calculate the fit value of the phenotype's characteristics, based on the environment parameters.
 */
object Fitter {

  type Range = (Int, Int)

  /**
   * Method to apply a fit value to a parameter, based on the operation passed.
   *
   * @param fitValue   the fit value.
   * @param parameter  the parameter to whom apply the fit value.
   * @param applyValue a strategy operation to apply.
   * @return the effective parameter.
   */

  def applyFitValue(fitValue: Double)(parameter: Int)(applyValue: (Double, Int) => Double): Int = applyValue(fitValue, parameter).toInt

  /**
   * Method to calculate the fit value, based on the environment parameters.
   *
   * @param phenotype   the phenotype ot the bee.
   * @param temperature the temperature of the environment.
   * @param pressure    the pressure of the environment.
   * @param humidity    the humidity of the environment.
   * @param calculate   a strategy to calculate the fitValue.
   * @param fitStrategy a strategy to calculate the fit on a single parameter.
   * @return the fit value.
   */
  def calculateFitValue(phenotype: Phenotype)(temperature: Int)(pressure: Int)(humidity: Int)
                       (calculate: (Double, Double, Double) => Double,
                        fitStrategy: Int => Range => Double = (property: Int) => (range: Range) =>
                          if (property in range) 1.0 else if (property < range) 1.0 - (range._1.toDouble - property.toDouble) / 100
                          else 1.0 - (property.toDouble - range._2.toDouble) / 100)
  : Double = {

    val tFit: Double = this.calculateFit(temperature)(phenotype.expressionOf(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY))(fitStrategy)
    val pFit: Double = this.calculateFit(pressure)(phenotype.expressionOf(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY))(fitStrategy)
    val hFit: Double = this.calculateFit(humidity)(phenotype.expressionOf(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))(fitStrategy)

    calculate(tFit, pFit, hFit)
  }

  /**
   * Defines how the fit value must be computed.
   *
   * @param property  the property of the environment.
   * @param range     the expression of the property in the phenotype.
   * @param calculate a strategy to calculate the fit of the parameter.
   * @return the fit value of the property.
   */
  private def calculateFit(property: Int)(range: Range)(calculate: Int => Range => Double): Double = {
    calculate(property)(range)
  }
}
