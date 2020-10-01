package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.GeneticInformation.GeneticInformation
import scala.model.bees.phenotype.CharacteristicTaxonomy

class GeneticInformationTest extends AnyFunSuite {
  val humidityInformation: GeneticInformation = GeneticInformation(GeneTaxonomy.HUMIDITY_GENE)

  val inf: String = "A GeneticInformation"

  test(s"$inf should not be empty") {
    assert(humidityInformation.information.nonEmpty)
  }

  test("The characteristics of a GeneticInformation should be the set of characteristic mapped by the genetic information"){
    assert(((humidityInformation characteristics) toSet) contains CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY)
  }

  test(s"$inf of the TemperatureGene should map only the temperature characteristic") {
    val info = GeneticInformation(GeneTaxonomy.TEMPERATURE_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY))
  }

  test(s"$inf of the PressureGene should map only the pressure characteristic") {
    val info = GeneticInformation(GeneTaxonomy.PRESSURE_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.PRESSURE_COMPATIBILITY))
  }

  test(s"$inf of the HumidityGene should map only the humidity characteristic") {
    val info = GeneticInformation(GeneTaxonomy.HUMIDITY_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY))
  }

  test(s"$inf of the AggressionGene should map only the aggression characteristic") {
    val info = GeneticInformation(GeneTaxonomy.AGGRESSION_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.AGGRESSION_RATE))
  }

  test(s"$inf of the ReproductionGene should map only the reproduction characteristic") {
    val info = GeneticInformation(GeneTaxonomy.REPRODUCTION_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.REPRODUCTION_RATE))
  }

  test(s"$inf of the LongevityGene should map only the temperature characteristic") {
    val info = GeneticInformation(GeneTaxonomy.LONGEVITY_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.LONGEVITY))
  }

  test(s"$inf of the GrowthGene should map the speed characteristic and the aggression characteristic") {
    val info = GeneticInformation(GeneTaxonomy.GROWTH_GENE)
    assert(((info characteristics) size) == 2 && (((info characteristics) toSet) contains CharacteristicTaxonomy.AGGRESSION_RATE)
      && (((info characteristics) toSet) contains CharacteristicTaxonomy.SPEED)
    )
  }

  test(s"$inf of the WingsGene should map only the speed characteristic") {
    val info = GeneticInformation(GeneTaxonomy.WINGS_GENE)
    assert(((info characteristics) size) == 1 && (((info characteristics) toSet) contains CharacteristicTaxonomy.SPEED))
  }

  test("A GenericInformation should return an empty optional if doesn't map a characteristic") {
    assert(humidityInformation influence CharacteristicTaxonomy.AGGRESSION_RATE isEmpty)
  }

  test("A GeneticInformation should define the influence of the gene on the characteristics mapped by it"){
    assert(humidityInformation influence CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY nonEmpty)
  }

}
