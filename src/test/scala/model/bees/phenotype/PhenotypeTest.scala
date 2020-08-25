package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.PhenotypeImpl

class PhenotypeTest extends AnyFunSuite{
  private val characteristicMap: Map[CharacteristicTaxonomy, Double] = Map((CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1.0))
  private val phenotype = PhenotypeImpl(Genotype.calculateExpression(Genotype()))

  test("A Phenotype should not be empty"){
    assert(phenotype.characteristics.nonEmpty)
  }

  test("A Phenotype should raise an IllegalArgumentException if not all the characteristics are mapped"){
    assertThrows[IllegalArgumentException](PhenotypeImpl(characteristicMap))
  }

  test("A Phenotype should always have all characteristics."){
    assert(phenotype.expressions.size == CharacteristicTaxonomy.maxId)
  }

  test("All the characteristic should be mapped into characterisicTaxonomy"){
    var contains: Boolean = true
    phenotype.characteristics.foreach(c => if (CharacteristicTaxonomy.values.contains(c.name)==false) contains = false)
    assert(contains)
  }

}
