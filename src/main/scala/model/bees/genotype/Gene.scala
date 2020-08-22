package scala.model.bees.genotype

import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.GeneticInformation.GeneticInformation

import scala.util.Random

/**
 * Object that represent a gene.
 */
object Gene {

  /**
   * The type of the frequency, expressed as an integer value.
   */
  type Frequency = Int

  val maxFrequency: Int = 100
  val minFrequency: Int = 1

  /**
   * Trait that represents a gene.
   */
  trait Gene {
    val name: GeneTaxonomy
    val frequency: Frequency
    val geneticInformation: GeneticInformation
    val isEnvironmental: Boolean
  }

  /**
   * Implementation of a gene.
   * @param name the taxonomy of the gene.
   * @param freq the frequency of the gene.
   * @param mapper an implicit strategy that map a gene to its genetic information.
   * @param env an implicit strategy that defines if a gene map an environmental characteristic.
   */
  case class GeneImpl(override val name: GeneTaxonomy, freq: Frequency = minFrequency + Random.nextInt(maxFrequency-minFrequency))
                     (implicit mapper: GeneTaxonomy => GeneticInformation,
                      implicit val env: GeneTaxonomy => Boolean) extends Gene{
    require(GeneTaxonomy.values.contains(name))
    override val frequency: Frequency = freq match {
      case i if i < minFrequency => minFrequency
      case i if i > maxFrequency => maxFrequency
      case _ => freq
    }
    override lazy val geneticInformation: GeneticInformation = mapper(name)
    override lazy val isEnvironmental: Boolean = env(name)
  }
}

