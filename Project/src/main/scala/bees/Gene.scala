package bees

import utility.RandomGenerator

object Gene {
  //Possibly adding an enum
  def apply( name: String, frequency: Int = RandomGenerator.getLowFrequency): Gene = {
    require(frequency >= 1 && frequency <= 9)
    name match {
      case "Temperature" => TemperatureCompatibilityGene(frequency)
      case "Pressure" => PressureCompatibilityGene(frequency)
      case "Humidity" => HumidityCompatibilityGene(frequency)
      case "Aggression" => AggressionGene(frequency)
      case "Reproduction" => ReproductionRateGene(frequency)
      case "Longevity" => LongevityGene(frequency)
      case "Color" => ColorGene(frequency)
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
