package bees

import bees.Gene.Gene

import scala.collection.immutable.HashSet

object Genotype {
  trait Genotype {
    val temperatureCompatibilityGene: Gene
    val humidityCompatibilityGene: Gene
    val pressureCompatibilityGene: Gene
    val aggressionGene: Gene
    val reproductionRateGene: Gene
    val longevityGene: Gene
    val colorGene: Gene

    def getGenes : Set[Gene]

  }

  case class GenotypeImpl(override val temperatureCompatibilityGene: Gene = Gene("Temperature"),
                          override val pressureCompatibilityGene: Gene = Gene("Pressure"),
                          override val humidityCompatibilityGene: Gene = Gene("Humidity"),
                          override val aggressionGene: Gene = Gene("Aggression"),
                          override val reproductionRateGene: Gene = Gene("Reproduction"),
                          override val longevityGene: Gene = Gene("Longevity"),
                          override val colorGene: Gene = Gene("Color")) extends Genotype {

    private var genes: Set[Gene] = HashSet(this.temperatureCompatibilityGene, this.pressureCompatibilityGene, this.humidityCompatibilityGene,
      this.aggressionGene, this.reproductionRateGene, this.longevityGene, this.colorGene)

    override def getGenes: Set[Gene] = this.genes
  }





}
