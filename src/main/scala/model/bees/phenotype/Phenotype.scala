package scala.model.bees.phenotype

import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

/**
 * Object that represents the phenotype.
 */
object Phenotype {

  private val defaultExpressionValue: Int = 1

  /**
   * Apply method for phenotype.
   * @param expressions a map of characteristic and its frequency.
   * @return a new phenotype.
   */
  def apply(expressions: Map[CharacteristicTaxonomy, Double]): Phenotype = PhenotypeImpl(expressions)

  /**
   * Trait for the phenotype.
   */
  trait Phenotype {

    val characteristics: Set[Characteristic]

    /**
     * Method that return the expression of a characteristic.
     * @param taxonomy the characteristic's name.
     * @return the characteristic's expression.
     */
    def expressionOf(taxonomy: CharacteristicTaxonomy): Characteristic#Expression


  }

  /**
   * Concrete implementation of phenotype.
   * @param expressions a map of characteristic and its frequency.
   */
  private case class PhenotypeImpl(expressions: Map[CharacteristicTaxonomy, Double]) extends Phenotype {
    require(expressions.size == CharacteristicTaxonomy.maxId)
    override val characteristics: Set[Characteristic] = expressions.map(kv => Characteristic(kv._1, kv._2)).toSet

    override def expressionOf(taxonomy: CharacteristicTaxonomy): Characteristic#Expression = characteristics.find(_.name.equals(taxonomy)).get.expression



  }
}
