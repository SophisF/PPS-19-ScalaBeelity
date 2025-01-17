package scala.model.bees.bee.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.EnvironmentInformation
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.model.environment.adapter.Cell

class FitCalculatorTest extends AnyFunSuite {


  test("The fit value for perfect range of values should be 1") {
    val maxFrequency = 100
    val genotype: Genotype = Genotype(
      Gene(GeneTaxonomy.TEMPERATURE_GENE, maxFrequency),
      Gene(GeneTaxonomy.PRESSURE_GENE, maxFrequency),
      Gene(GeneTaxonomy.HUMIDITY_GENE, maxFrequency))
    val phenotype: Phenotype = genotype expressInPhenotype

    assert(FitCalculator.calculateFitValue(phenotype)(EnvironmentInformation(Seq(Cell(36, 80, 1050))))(
      params => params.sum / params.size) == 1)
  }

  test("The fit value should be between 0 and 1 if the environmental expressions not corresponds to environment parameters") {
    val middleFrequency = 50
    val genotype: Genotype = Genotype(
      Gene(GeneTaxonomy.TEMPERATURE_GENE, middleFrequency),
      Gene(GeneTaxonomy.PRESSURE_GENE, middleFrequency),
      Gene(GeneTaxonomy.HUMIDITY_GENE, middleFrequency))
    val phenotype: Phenotype = genotype expressInPhenotype
    val fitValue = FitCalculator.calculateFitValue(phenotype)(EnvironmentInformation(Seq(Cell())))(
      params => params.sum / params.size)

    assert(fitValue >= 0 && fitValue < 1)
  }

  test("Apply a fit value to a parameter means apply an operation between the parameter and the fit value") {
    assert(FitCalculator.applyFitValue(0.5)(50)(_ * _) == 25)
  }
}