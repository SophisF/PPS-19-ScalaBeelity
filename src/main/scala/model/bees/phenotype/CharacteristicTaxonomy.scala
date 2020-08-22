package scala.model.bees.phenotype

/**
 * The taxonomy of the characteristic. It's an enumeration that defines the names of all characteristics.
 */
object CharacteristicTaxonomy extends Enumeration {
  type CharacteristicTaxonomy = Value
  val TEMPERATURE_COMPATIBILITY, PRESSURE_COMPATIBILITY, HUMIDITY_COMPATIBILITY, AGGRESSION_RATE, REPRODUCTION_RATE,
      LONGEVITY, COLOR, SPEED = Value
}
