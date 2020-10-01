package scala.model.environment.property.utils

import breeze.linalg.DenseMatrix

import scala.model.environment.property.Property

/**
 * Adds utility to generate a filter
 */
private[environment] trait FilterGenerator { this: Property =>

  /**
   * Generates a filter of variations
   *
   * @param xDecrement influence the width of the filter
   * @param yDecrement influence the height of the filter
   * @return the filter of variations
   */
  def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType]
}
