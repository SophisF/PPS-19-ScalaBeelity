package bees.genotype

import bees.genotype.Genotype.Genotype
import bees.phenotype.CharacteristicTaxonomy
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy

object GeneExpressor {

  //il double viene normalizzato sfruttando la somma dei valori di influenza dei geni che influenzano la caratteristica
  def mapGenotypeToCharacteristics(genotype: Genotype): Set[(CharacteristicTaxonomy, Double)] = {
    CharacteristicTaxonomy.values.map(taxonomy => (taxonomy, genotype.getGenes.foldRight(0.0)((gene, accumulator) => {
      val influence = gene.information.getInfluence(taxonomy)
      influence match {
        case Some(_) => accumulator + influence.get.influenceValue
        case None => accumulator
      }
    })))
  }


}
