package bees.genotype


import bees.genotype.Gene.{Gene, GeneImpl}
import bees.genotype.GeneManager._
import bees.genotype.GeneTaxonomy.GeneTaxonomy
import bees.genotype.Genotype.Genotype
import bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import bees.phenotype.Phenotype.Phenotype
import bees.phenotype.Characteristic._

import scala.collection.immutable.HashSet
import scala.util.Random

object GeneticEvolver {

  implicit class MyInt(number: Int) {
    def in(tuple: (Int, Int)): Boolean = number >= tuple._1 && number <= tuple._2

    def <(tuple: (Int, Int)): Boolean = number < tuple._1

    def >(tuple: (Int, Int)): Boolean = number > tuple._2
  }

  //TODO prenderà info riguardo all'ambiente
  def buildGenotype(genotype: Genotype)(phenotype: Phenotype)(temperature: Int): Unit = {
    println(genotype.genes)
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
    println(genes)



  }

  def calculateEnvironment(genotype: Genotype)(phenotype: Phenotype)(parameter: Int)
                          (geneTaxonomy: GeneTaxonomy)(characteristicTaxonomy: CharacteristicTaxonomy): Gene = {
    val frequency: Int =  genotype.genes.find(_.name.equals(geneTaxonomy)).get.frequency
    val characteristic: Option[Characteristic] = phenotype.characteristicByTaxonomy(characteristicTaxonomy)

    if(characteristic.nonEmpty && (parameter in characteristic.get.expression.asInstanceOf[(Int, Int)]))
      GeneImpl(geneTaxonomy, frequency)
    else if(characteristic.nonEmpty && parameter < characteristic.get.expression.asInstanceOf[(Int, Int)])
      GeneImpl(geneTaxonomy, frequency - 2)
    else   GeneImpl(geneTaxonomy, frequency + 2)

  }






}
