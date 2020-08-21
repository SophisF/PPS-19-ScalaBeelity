package scala.model.bees.bee

import scala.model.bees.utility.PimpInt._

object Fitter {

  type Range = (Int, Int)

  def calculateFit(property: Int)(range: Range): Double = {
    if(property in range) 1.0 else if(property < range) property.toDouble / range._1.toDouble
    else range._2.toDouble / property.toDouble
  }

}
