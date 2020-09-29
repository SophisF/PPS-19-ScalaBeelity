package scala.model.environment.property.realization.integer.utils

import breeze.linalg.DenseMatrix

import scala.model.environment.property.GaussianFilterBuilder.function3d
import scala.model.environment.property.realization.integer.Property
import scala.model.environment.property.utils.{FilterGenerator => Utils}
import scala.utility.IterableHelper.RichIterable
import scala.utility.MathHelper.intValueOf

trait FilterGenerator extends Utils { this: Property =>

  def filter(xDecrement: Int, yDecrement: Int)(implicit minValue: Int, maxValue: Int): DenseMatrix[Double] =
    function3d((minValue to maxValue).filter(_ != 0).random.get, 0, xDecrement, yDecrement)

  override def generateFilter(xDecrement: Int, yDecrement: Int): DenseMatrix[VariationType] =
    filter(xDecrement, yDecrement)(zeroCenteredRange._1, zeroCenteredRange._2).map(variation(_))
}
