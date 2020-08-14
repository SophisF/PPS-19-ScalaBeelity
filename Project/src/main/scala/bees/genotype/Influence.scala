package bees.genotype

import bees.genotype.InfluenceType.InfluenceType

object InfluenceType extends Enumeration {
  type InfluenceType = Value
  val POSITIVE, NEGATIVE = Value
}

object Influence {
  trait Influence{
    val typeOfInfluence: InfluenceType
    val influenceValue: Double

    def +(value: Double): Double = value + this.influenceValue
  }

  case class InfluenceImpl(override val typeOfInfluence: InfluenceType = InfluenceType.POSITIVE,
                           influenceInPercentage: Int = 100) extends Influence{
    require(InfluenceType.values.contains(typeOfInfluence) &&
      influenceInPercentage >= 0 && influenceInPercentage <= 100)

    private def getDoubleInfluence = influenceInPercentage.toDouble / 100

    override val influenceValue: Double = this.typeOfInfluence match {
      case InfluenceType.POSITIVE => getDoubleInfluence
      case _ => - getDoubleInfluence
    }
  }
}
