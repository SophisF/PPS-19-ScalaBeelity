package scala.model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.HashSet
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.genotype.{Gene, GeneTaxonomy, Genotype}
import scala.model.bees.phenotype.Phenotype.Phenotype

class FitterTest extends AnyFunSuite {
  val maxTemperature = 36
  val maxPressure = 1050
  val maxHumidity = 80

  test("The fit value for perfect range of values should be 1"){
    val maxFrequency = 100
    val genotype: Genotype = Genotype(HashSet(
      Gene(GeneTaxonomy.TEMPERATURE_GENE, maxFrequency),
      Gene(GeneTaxonomy.PRESSURE_GENE, maxFrequency),
      Gene(GeneTaxonomy.HUMIDITY_GENE, maxFrequency)))
    val phenotype: Phenotype = genotype expressItself;
    assert(Fitter.calculateFitValue(phenotype)(maxTemperature)(maxPressure)(maxHumidity)((temperature, pressure, humidity) => (temperature + pressure + humidity) / 3) == 1)
  }

  test("The fit value should be between 0 and 1 if the environmental expressions not corresponds to environment parameters"){
    val middleFrequency = 50
    val genotype: Genotype = Genotype(HashSet(
      Gene(GeneTaxonomy.TEMPERATURE_GENE, middleFrequency),
      Gene(GeneTaxonomy.PRESSURE_GENE, middleFrequency),
      Gene(GeneTaxonomy.HUMIDITY_GENE, middleFrequency)))
    val phenotype: Phenotype = genotype expressItself
    val fitValue = Fitter.calculateFitValue(phenotype)(maxTemperature)(maxPressure)(maxHumidity)((temperature, pressure, humidity) => (temperature + pressure + humidity) / 3)
    assert(fitValue > 0 && fitValue < 1)
  }

  test("Apply a fit value to a parameter means apply an operation between the parameter and the fit value"){
    assert(Fitter.applyFitValue(0.5)(50)(_ * _) == 25)
  }
}
