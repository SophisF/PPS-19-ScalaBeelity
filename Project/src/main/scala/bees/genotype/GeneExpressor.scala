package bees.genotype

import bees.genotype.Gene.{CodingInformation, Gene}
import bees.genotype.Genotype.Genotype
import bees.phenotype.CharacteristicTaxonomy
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object GeneExpressor {

  private def calculateExpressions(genotype: => Genotype): Set[(CharacteristicTaxonomy, Double)] =
    CharacteristicTaxonomy.values.map(taxonomy => (taxonomy, genotype.getGenes.foldRight(0.0)((gene, accumulator) => {
      gene match {
        case _: Gene with CodingInformation =>
          val influence = gene.asInstanceOf[Gene with CodingInformation].information.getInfluence(taxonomy)
          influence match {
            case Some(_) => influence.get + accumulator
            case None => accumulator
          }
        case _ => accumulator
      }
    })))



  private def calculateNormalizationFactors(genotype: => Genotype): Set[(CharacteristicTaxonomy, Double)] =
    CharacteristicTaxonomy.values.map(taxonomy => (taxonomy, genotype.getGenes.toSet.filter(
      gene => gene match {
        case _: Gene with CodingInformation => gene.asInstanceOf[Gene with CodingInformation].information
          .getInfluence(taxonomy) match {
          case Some(_) => true
          case _ => false
        }
        case _ => false
      }).toList.map(_.asInstanceOf[Gene with CodingInformation].information.getInfluence(taxonomy)
      .get.influenceValue).foldRight(0.0)(_ + _)))


  private def normalizeExpressions(genotype: Genotype): Set[(CharacteristicTaxonomy, Double)] = {
    val expressions: Set[(CharacteristicTaxonomy, Double)] = this.calculateExpressions(genotype)
    val normalizationFactors: Set[(CharacteristicTaxonomy, Double)] = this.calculateNormalizationFactors(genotype)

    println(expressions)
    println(normalizationFactors)
    expressions map (exp => (exp._1, exp._2 / (normalizationFactors.find(_._1.equals(exp._1)) match {
      case Some(normalizer) => normalizer._2
      case _ => 1.0
    })))


  }

  def mapGenotypeToCharacteristics(genotype: Genotype): Set[(CharacteristicTaxonomy, Double)] = {
    normalizeExpressions(genotype)
  }


}
