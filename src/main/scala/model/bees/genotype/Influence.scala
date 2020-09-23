package scala.model.bees.genotype

import scala.model.bees.genotype.InfluenceType.InfluenceType
import scala.utility.PimpInt._

/**
 * Enumeration of the influence types.
 */
object InfluenceType extends Enumeration {
  type InfluenceType = Value
  val POSITIVE, NEGATIVE = Value
}

/**
 * Represent the influence of a gene on a characteristic.
 */
object Influence {

  /**
   * Apply method for influence.
   * @param typeOfInfluence the type of influence.
   * @param influenceInPercentage the value of the influence, in percentage.
   * @return a new influence.
   */
  def apply(typeOfInfluence: InfluenceType = InfluenceType.POSITIVE,
            influenceInPercentage: Int = 100): Influence = InfluenceImpl(typeOfInfluence, influenceInPercentage)

  /**
   * Trait for the influence.
   */
  trait Influence {
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
   *
   * @constructor creates a new influence by the type and a value in percentage.
   * @param typeOfInfluence       the type of the influence
   * @param influenceInPercentage the value of the influence in percentage.
   */
  private case class InfluenceImpl(override val typeOfInfluence: InfluenceType,
                           influenceInPercentage: Int) extends Influence {
    require(InfluenceType.values.contains(typeOfInfluence) &&
      influenceInPercentage >= 0 && influenceInPercentage <= 100)

    /**
     * Private method that convert the value in percentage to the real value.
     *
     * @return the influence value
     */
    private def convertInfluence: Double = influenceInPercentage.toInfluenceValue / 100

    /**
     * The value of the influence.
     */
    override lazy val influenceValue: Double = this.typeOfInfluence match {
      case InfluenceType.POSITIVE => convertInfluence
      case _ => -convertInfluence
    }
  }

}
