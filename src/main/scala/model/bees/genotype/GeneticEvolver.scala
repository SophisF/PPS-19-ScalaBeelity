package scala.model.bees.genotype


import scala.model.bees.genotype.Gene.{Gene, GeneImpl}
import scala.model.bees.genotype.GeneManager._
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Characteristic.{Characteristic, RangeExpression}
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.utility.PimpInt._
import scala.util.Random

/**
 * Singleton that manages the evolution of the bees.
 */
object GeneticEvolver {

  private val evolutionaryRate: Int = 2

  /**
   * Method that build a new genotype with a better adaptation to the environment and casual variations.
   * @param genotype the existing genotype.
   * @param phenotype the existing phenotype.
   * @param temperature the temperature parameter of the environment.
   * @param pressure the pressure parameter of the environment.
   * @param humidity the humidity parameter of the environment.
   * @return a new genotype with better adaptation to the environment and casual variations.
   */
  def buildGenotype(genotype: Genotype)(phenotype: Phenotype)(temperature: Int)(pressure: Int)(humidity: Int): Set[Gene] = {

    var genes: Set[Gene] = Set.empty
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).map(gene => environmentalAdaptation(genotype)(phenotype)
    (temperature)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.PRESSURE_GENE)).map(gene => environmentalAdaptation(genotype)(phenotype)
    (pressure)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.HUMIDITY_GENE)).map(gene => environmentalAdaptation(genotype)(phenotype)
    (humidity)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    genes = genes ++ genotype.genes.filterNot(_.isEnvironmental).map(gene => GeneImpl(gene.name, if (Random.nextInt() % 2 == 0)
      gene.frequency - evolutionaryRate else gene.frequency + evolutionaryRate))

    genes

  }

  /**
   * Generic method that products the environmental adaptation.
   * @param genotype the existing genotype.
   * @param phenotype the existing phenotype.
   * @param parameter the environmental parameter.
   * @param geneTaxonomy the taxonomy of the gene.
   * @param characteristicTaxonomy the taxonomy of the characteristic expressed by the gene.
   * @return a new gene with a frequency that fit better the environment.
   */
  private def environmentalAdaptation(genotype: Genotype)(phenotype: Phenotype)(parameter: Int)
                          (geneTaxonomy: GeneTaxonomy)(characteristicTaxonomy: CharacteristicTaxonomy): Gene = {
    val frequency: Int = genotype.frequency(geneTaxonomy)
    val characteristicOpt: Option[Characteristic] = phenotype.characteristics.find(_.name.equals(characteristicTaxonomy))
    val expressionOpt: Option[(Int, Int)] = if (characteristicOpt.nonEmpty && characteristicOpt.get.isInstanceOf[Characteristic with RangeExpression])
      Some(characteristicOpt.get.asInstanceOf[Characteristic with RangeExpression].expression) else None

    expressionOpt match {
      case Some(expression) => if (parameter in expression) GeneImpl(geneTaxonomy, frequency)
      else if (parameter < expression)
        GeneImpl(geneTaxonomy, frequency - evolutionaryRate)
      else GeneImpl(geneTaxonomy, frequency + evolutionaryRate)
      case _ => GeneImpl(geneTaxonomy, frequency)
    }


  }


}
