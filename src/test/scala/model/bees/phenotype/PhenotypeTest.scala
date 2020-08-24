package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.PhenotypeImpl

class PhenotypeTest extends AnyFunSuite{
  private val characteristicMap: Map[CharacteristicTaxonomy, Double] = Map((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1.0))
  private val phenotype = PhenotypeImpl(Genotype.calculateExpression(Genotype()))

  test("A Phenotype should raise an IllegalArgumentException if not all the characteristics are mapped"){
    assertThrows[IllegalArgumentException](PhenotypeImpl(characteristicMap))
  }

  test("A Phenotype should always has all characteristics."){
    assert(phenotype.expressions.size == CharacteristicTaxonomy.maxId)
  }


}
