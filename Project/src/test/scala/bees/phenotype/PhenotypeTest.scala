package bees.phenotype

import bees.genotype.Genotype
import bees.genotype.Genotype.GenotypeImpl
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import bees.phenotype.Phenotype.{Phenotype, PhenotypeImpl}
import org.scalatest.funsuite.AnyFunSuite

class PhenotypeTest extends AnyFunSuite{
  private val characteristicMap: Map[CharacteristicTaxonomy, Double] = Map((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1.0))
  private val phenotype: Phenotype = PhenotypeImpl(Genotype.calculateExpression(GenotypeImpl()))

  test("A Phenotype should raise an IllegalArgumentException if not all the characteristics are mapped"){
    assertThrows[IllegalArgumentException](PhenotypeImpl(characteristicMap))
  }

  test("A Phenotype should always has all characteristics."){
    assert(phenotype.characteristics.size == CharacteristicTaxonomy.maxId)
  }


}
