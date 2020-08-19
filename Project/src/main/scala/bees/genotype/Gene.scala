package bees.genotype

import bees.genotype.GeneTaxonomy.GeneTaxonomy
import bees.genotype.GeneticInformation.GeneticInformation

import scala.util.Random

/**
 * Object that represent a gene.
 */
object Gene {
  type Frequency = Int

  val maxFrequency: Int = 100
  val minFrequency: Int = 1

  trait Gene {
    val name: GeneTaxonomy
    val frequency: Frequency
    val geneticInformation: GeneticInformation
    val isEnvironmental: Boolean
  }

  case class GeneImpl(override val name: GeneTaxonomy, freq: Frequency = minFrequency + Random.nextInt(maxFrequency-minFrequency))
                     (implicit mapper: GeneTaxonomy => GeneticInformation,
                      implicit val env: GeneTaxonomy => Boolean) extends Gene{
    require(GeneTaxonomy.values.contains(name))
    override val frequency: Frequency = freq match {
      case i if i < minFrequency => minFrequency
      case i if i > maxFrequency => maxFrequency
      case _ => freq
    }
    override val geneticInformation: GeneticInformation = mapper(name)
    override val isEnvironmental: Boolean = env(name)
  }
}

