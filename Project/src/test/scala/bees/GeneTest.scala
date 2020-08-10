package bees

import bees.Gene._
import org.scalatest.funsuite.AnyFunSuite
import utility.RandomGenerator

import scala.collection.immutable.HashSet

class GeneTest extends AnyFunSuite{
  val temperatureCompatibilityGene: Gene = Gene("Temperature")
  val pressureCompatibilityGene: Gene = Gene("Pressure")
  val humidityCompatibilityGene: Gene = Gene("Humidity")
  val aggressionGene: Gene = Gene("Aggression")
  val reproductionRateGene: Gene = Gene("Reproduction")
  val longevityGene: Gene = Gene("Longevity")
  val colorGene: Gene = Gene("Color")

  var genes: Set[Gene] = HashSet(temperatureCompatibilityGene, pressureCompatibilityGene, humidityCompatibilityGene,
    aggressionGene, reproductionRateGene, longevityGene, colorGene)

  val temperatureCompatibilityGeneWithMediumFrequency: Gene = Gene("Temperature", RandomGenerator.getMediumFrequency)
  val temperatureCompatibilityGeneWithHighFrequency: Gene = Gene("Temperature", RandomGenerator.getHighFrequency)

  test("Any Gene should have a name"){
    val errorGenes = for {
      gene <- genes
      if gene.name == null
    } yield gene
    assert(errorGenes.isEmpty)
  }

  test("Any Gene should have default Frequency value between 1 and 3 include"){
    val errorGenes = for {
      gene <- genes
      if gene.frequency < 1 || gene.frequency > 3
    } yield gene
    assert(errorGenes.isEmpty)
  }

  test("A Gene with medium Frequency should have Frequency value between 4 and 6 include") {
    assert(temperatureCompatibilityGeneWithMediumFrequency.frequency >= 4 &&
      temperatureCompatibilityGeneWithMediumFrequency.frequency <= 6)
  }

  test("A Gene with high Frequency should have Frequency value between 7 and 9 include") {
    assert(temperatureCompatibilityGeneWithHighFrequency.frequency >= 7 &&
      temperatureCompatibilityGeneWithHighFrequency.frequency <= 9)
  }

}
