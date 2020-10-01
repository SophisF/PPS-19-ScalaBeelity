package scala.model.bees.phenotype

import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.adapter.Cell
import scala.utility.PimpIterable._

/**
 * Object that models how the context of the environment can influence the bees.
 */
private[model] object EnvironmentInformation {

  /**
   * Method that defines the default strategy of influence, based on the average values of the environment.
   * @param aggregate a sequence of value.
   * @return the average of the aggregate's values.
   */
  private def defaultStrategy(aggregate: Seq[Int]): Int = aggregate.average

  /**
   * Apply method for the EnvironmentInformation.
   * @param cell a sequence of cell of the environment.
   * @param strategy a strategy that defines how to calculate the influence on the environmental characteristics.
   * @return a new EnvironmentInformation.
   */
  def apply(cell: Seq[Cell], strategy: Seq[Int] => Int = this.defaultStrategy): EnvironmentInformation = new EnvironmentInformation {
    override val characteristicMap: Map[CharacteristicTaxonomy, Int] =
      Set(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY -> strategy(cell.map(_.temperature())),
        CharacteristicTaxonomy.PRESSURE_COMPATIBILITY -> strategy(cell.map(_.pressure())),
        CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY -> strategy(cell.map(_.humidity())),
      ).toMap
  }

  /**
   * Trait for the environment information.
   */
  sealed trait EnvironmentInformation {
    val characteristicMap: Map[CharacteristicTaxonomy, Int]
  }

}
