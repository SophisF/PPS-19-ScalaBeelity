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

  case class GenotypeImpl(override val temperatureCompatibilityGene: Gene = Gene(GeneTaxonomy.TEMPERATURE),
                          override val pressureCompatibilityGene: Gene = Gene(GeneTaxonomy.PRESSURE),
                          override val humidityCompatibilityGene: Gene = Gene(GeneTaxonomy.HUMIDITY),
                          override val aggressionGene: Gene = Gene(GeneTaxonomy.AGGRESSION),
                          override val reproductionRateGene: Gene = Gene(GeneTaxonomy.REPRODUCTION),
                          override val longevityGene: Gene = Gene(GeneTaxonomy.LONGEVITY),
                          override val colorGene: Gene = Gene(GeneTaxonomy.COLOR)) extends Genotype {

    private var genes: Set[Gene] = HashSet(this.temperatureCompatibilityGene, this.pressureCompatibilityGene, this.humidityCompatibilityGene,
      this.aggressionGene, this.reproductionRateGene, this.longevityGene, this.colorGene)

    override def getGenes: Set[Gene] = this.genes
  }





}
