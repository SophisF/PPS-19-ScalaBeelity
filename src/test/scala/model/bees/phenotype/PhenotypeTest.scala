package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

class PhenotypeTest extends AnyFunSuite{
  private val characteristicMap: Map[CharacteristicTaxonomy, Double] = Map((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1.0))
  private val phenotype = Genotype() expressInPhenotype

  test("A Phenotype should not be empty"){
    assert(phenotype.characteristics.nonEmpty)
  }

  test("A Phenotype should always have all characteristics."){
    assert(phenotype.characteristics.size == CharacteristicTaxonomy.maxId)
  }

  test("A Phenotype should raise an IllegalArgumentException if not all the characteristics are mapped"){
    assertThrows[IllegalArgumentException](Phenotype(characteristicMap))
  }

  test("All the characteristic should be mapped into characteristicTaxonomy"){
    var contains: Boolean = true
    phenotype.characteristics.foreach(c => if (!CharacteristicTaxonomy.values.contains(c.taxonomy)) contains = false)
    assert(contains)
  }

}
