package scala.model.bees.genotype

import scala.model.bees.genotype.InfluenceType.InfluenceType

/**
Enumeration of the influence types.
 */
object InfluenceType extends Enumeration {
  type InfluenceType = Value
  val POSITIVE, NEGATIVE = Value
}

/**
Represent the influence of a gene on a characteristic.
 */
object Influence {

  /**
   * Implicit class to convert from influence in percentage to influence value.
   * @param value the influence percentage.
   */
  implicit class InfluenceValue(value: Int){
    /**
     * Convert the type of influence in percentage from Int to InfluenceValue.
     * @return the influence value converted.
     */
    def toInfluenceValue: Double = value.toDouble
  }

  /**
   * Trait for the influence.
   */
  trait Influence{
    /**
     * The type of the influence: positive or negative.
     */
    val typeOfInfluence: InfluenceType
    /**
     * The value of the influence.
     */
    val influenceValue: Double


  }

  /**
   * Case class for the influence.
   * @constructor creates a new influence by the type and a value in percentage.
   * @param typeOfInfluence the type of the influence
   * @param influenceInPercentage the value of the influence in percentage.
   */
  case class InfluenceImpl(override val typeOfInfluence: InfluenceType = InfluenceType.POSITIVE,
                           influenceInPercentage: Int = 100) extends Influence{
    require(InfluenceType.values.contains(typeOfInfluence) &&
      influenceInPercentage >= 0 && influenceInPercentage <= 100)

    /**
     * Private method that convert the value in percentage to the real value.
     * @return the influence value
     */
    private def convertInfluence: Double = influenceInPercentage.toInfluenceValue / 100

    /**
     * The value of the influence.
     */
    override val influenceValue: Double = this.typeOfInfluence match {
      case InfluenceType.POSITIVE => convertInfluence
      case _ => - convertInfluence
    }
  }
}
