package bees.genotype

object GeneTaxonomy extends Enumeration {
  type GeneTaxonomy = Value
  val TEMPERATURE_GENE, PRESSURE_GENE, HUMIDITY_GENE, AGGRESSION_GENE, REPRODUCTION_GENE, LONGEVITY_GENE, COLOR_GENE,
      GROWTH_GENE, WINGS_GENE = Value
}
