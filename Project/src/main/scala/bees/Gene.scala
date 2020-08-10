package bees

import bees.GeneTaxonomy.GeneTaxonomy
import utility.RandomGenerator

object GeneTaxonomy extends Enumeration {
  type GeneTaxonomy = Value
  val TEMPERATURE, PRESSURE, HUMIDITY, AGGRESSION, REPRODUCTION, LONGEVITY, COLOR = Value
}

object Gene {

  def apply( geneTaxonomy: GeneTaxonomy, frequency: Int = RandomGenerator.getLowFrequency): Gene = {
    require(frequency >= 1 && frequency <= 9)
    geneTaxonomy match {
      case GeneTaxonomy.TEMPERATURE => TemperatureCompatibilityGene(frequency)
      case GeneTaxonomy.PRESSURE => PressureCompatibilityGene(frequency)
      case GeneTaxonomy.HUMIDITY => HumidityCompatibilityGene(frequency)
      case GeneTaxonomy.AGGRESSION => AggressionGene(frequency)
      case GeneTaxonomy.REPRODUCTION => ReproductionRateGene(frequency)
      case GeneTaxonomy.LONGEVITY => LongevityGene(frequency)
      case GeneTaxonomy.COLOR => ColorGene(frequency)
      case _ => null
    }
  }

  trait Gene {
    val name: String
    val frequency: Int
  }

  private case class TemperatureCompatibilityGene(override val frequency: Int) extends Gene{
    override val name: String = "Temperature"
  }
  private case class PressureCompatibilityGene(override val frequency: Int) extends Gene{
    override val name: String = "Pressure"
  }
  private case class HumidityCompatibilityGene(override val frequency: Int) extends Gene{
    override val name: String = "Humidity"
  }
  private case class AggressionGene(override val frequency: Int) extends Gene{
    override val name: String = "Aggression"
  }
  private case class ReproductionRateGene(override val frequency: Int) extends Gene{
    override val name: String = "Reproduction"
  }
  private case class LongevityGene(override val frequency: Int) extends Gene{
    override val name: String = "Longevity"
  }
  private case class ColorGene(override val frequency: Int) extends Gene{
    override val name: String = "Color"
  }

}
