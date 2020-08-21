package scala.model.bees.genotype


import scala.model.bees.genotype.Gene.{Gene, GeneImpl}
import scala.model.bees.genotype.GeneManager._
import scala.model.bees.genotype.GeneTaxonomy.GeneTaxonomy
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.bees.utility.PimpInt._

import scala.collection.immutable.HashSet
import scala.util.Random

object GeneticEvolver {

  //TODO prenderà info riguardo all'ambiente
  def buildGenotype(genotype: Genotype)(phenotype: Phenotype)(temperature: Int): Unit = {

    var genes: Set[Gene] = HashSet()
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).map(gene => calculateEnvironment(genotype)(phenotype)
    (temperature)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    //pressione
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).map(gene => calculateEnvironment(genotype)(phenotype)
    (temperature)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    //umidità
    genes = genes ++ genotype.genes.filter(_.name.equals(GeneTaxonomy.TEMPERATURE_GENE)).map(gene => calculateEnvironment(genotype)(phenotype)
    (temperature)(gene.name)(gene.geneticInformation.characteristics.head)
    )
    genes = genes ++ genotype.genes.filterNot(_.isEnvironmental).map(gene => GeneImpl(gene.name, if(Random.nextInt() % 2 == 0) gene.frequency -2 else gene.frequency + 2 ))




  }

  def calculateEnvironment(genotype: Genotype)(phenotype: Phenotype)(parameter: Int)
                          (geneTaxonomy: GeneTaxonomy)(characteristicTaxonomy: CharacteristicTaxonomy): Gene = {
    val frequency: Int =  if(genotype.genes.exists(_.name.equals(geneTaxonomy))) genotype.genes.find(_.name.equals(geneTaxonomy)).get.frequency else 1
    val expression: (Int, Int) = if(phenotype.environmentalCharacteristics.exists(_.name.equals(characteristicTaxonomy)))
      phenotype.environmentalCharacteristics.find(_.name.equals(characteristicTaxonomy)).get.expression else (0, 0)

    if(parameter in expression)
      GeneImpl(geneTaxonomy, frequency)
    else if(parameter < expression)
      GeneImpl(geneTaxonomy, frequency - 2)
    else   GeneImpl(geneTaxonomy, frequency + 2)

  }



}
