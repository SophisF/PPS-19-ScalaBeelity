package scala.model.bees.genotype

/**
 * The taxonomy of the genes. It's an enumeration that defines the names of all genes.
 */
object GeneTaxonomy extends Enumeration {
  type GeneTaxonomy = Value
  val TEMPERATURE_GENE, PRESSURE_GENE, HUMIDITY_GENE, AGGRESSION_GENE, REPRODUCTION_GENE, LONGEVITY_GENE,
      GROWTH_GENE, WINGS_GENE = Value
}
