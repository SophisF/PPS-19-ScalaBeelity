package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.Cell
import scala.utility.PimpIterable._

object EnvironmentInformation {

  private def defaultStrategy(sum: Seq[Int]): Int = sum.average

  def apply(cell: Seq[Cell], strategy: Seq[Int] => Int = this.defaultStrategy): EnvironmentInformation = new EnvironmentInformation {
    override val characteristicMap: Map[CharacteristicTaxonomy, Int] =
      Set(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY -> strategy(cell.map(_.temperature.numericRepresentation(false))),
        CharacteristicTaxonomy.PRESSURE_COMPATIBILITY -> strategy(cell.map(_.pressure.numericRepresentation(false))),
        CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY -> strategy(cell.map(_.humidity.numericRepresentation(false))),
      ).toMap
  }

  trait EnvironmentInformation {
    val characteristicMap: Map[CharacteristicTaxonomy, Int]
  }

}
