package bees.phenotype

object CharacteristicTaxonomy extends Enumeration {
  type CharacteristicTaxonomy = Value
  val TEMPERATURE_COMPATIBILITY, PRESSURE_COMPATIBILITY, HUMIDITY_COMPATIBILITY, AGGRESSION_RATE, REPRODUCTION_RATE,
      LONGEVITY, COLOR, SPEED = Value
}
