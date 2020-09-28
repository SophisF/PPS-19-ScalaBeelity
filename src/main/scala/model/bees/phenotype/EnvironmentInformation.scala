package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.Cell
import scala.utility.PimpIterable._

object EnvironmentInformation {

  def apply(cell: Cell*): EnvironmentInformation = new EnvironmentInformation {
    override val characteristicMap: Map[CharacteristicTaxonomy, Int] =
      Set(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY -> cell.map(_.temperature.numericRepresentation(false)).average,
        CharacteristicTaxonomy.PRESSURE_COMPATIBILITY -> cell.map(_.pressure.numericRepresentation(false)).average,
        CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY -> cell.map(_.humidity.numericRepresentation(false)).average,
      ).toMap
  }

  trait EnvironmentInformation {
    val characteristicMap: Map[CharacteristicTaxonomy, Int]
  }

}
