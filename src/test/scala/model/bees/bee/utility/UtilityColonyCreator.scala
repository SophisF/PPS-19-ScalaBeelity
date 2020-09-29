package scala.model.bees.bee.utility

import scala.model.bees.bee.Colony.Colony
import scala.model.bees.bee.{Bee, Colony, Queen}
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Characteristic.{HumidityCompatibilityCharacteristic, PressureCompatibilityCharacteristic, TemperatureCompatibilityCharacteristic}
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.environment.Cell
import scala.util.Random
import scala.utility.Point

object UtilityColonyCreator {

  val maxTemperature: Int = TemperatureCompatibilityCharacteristic.max
  val maxPressure: Int = PressureCompatibilityCharacteristic.max
  val maxHumidity: Int = HumidityCompatibilityCharacteristic.max

  private val age = 0

  def createColony(genotype: Genotype, position: Point): Colony = {
    Colony(Random.nextDouble(), Queen(None, genotype, genotype expressInPhenotype, age, position, null, EnvironmentInformation(Seq(Cell()))),
      (1 to 10).map(_ => Bee(genotype, genotype expressInPhenotype, age, EnvironmentInformation(Seq(Cell())))).toSet)
  }


}
