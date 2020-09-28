package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene
import scala.model.bees.phenotype.Characteristic._
import scala.util.Random


class CharacteristicTest extends AnyFunSuite{
  private val temperatureCharacteristic = Characteristic(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1))
  private val pressureCharacteristic = Characteristic(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1))
  private val humidityCharacteristic = Characteristic(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1))
  private val aggressionRateCharacteristic = Characteristic(CharacteristicTaxonomy.AGGRESSION_RATE, 1 + Random.nextDouble() * (Gene.maxFrequency-1))

  test("An environmental characteristic should have a Range expression"){
    assert(temperatureCharacteristic.expression.isInstanceOf[(Int, Int)] &&
    pressureCharacteristic.expression.isInstanceOf[(Int, Int)] &&
    humidityCharacteristic.expression.isInstanceOf[(Int, Int)])
  }

  test("A non environmental characteristic should have a Int expression"){
    assert(aggressionRateCharacteristic.expression.isInstanceOf[Int])
  }

  // Test also for ExpressionMapper
  test("A TemperatureCompatibilityCharacteristic should have expression between 18 and 36"){
    val range: (Int, Int) = temperatureCharacteristic.expression

    assert(range._1 >= 18 && range._2 <= 36)
  }

  test("A HumidityCompatibilityCharacteristic should have expression between 40 and 80"){
    val range: (Int, Int) = humidityCharacteristic.expression

    assert(range._1 >= 40 && range._2 <= 80)
  }

  test("A PressureCompatibilityCharacteristic should have expression between 1000 and 1050"){
    val range: (Int, Int) = pressureCharacteristic.expression

    assert(range._1 >= 1000 && range._2 <= 1050)
  }

  test("A AggressionRateCharacteristic should have expression between 1 and 10"){
    val expression: Int = aggressionRateCharacteristic.expression

    assert(expression >= 1 && expression <= 10)
  }


  test("A ReproductionRateCharacteristic should have expression between 1 and 5"){
    val expression: Int = Characteristic(CharacteristicTaxonomy.REPRODUCTION_RATE, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).expression

    assert(expression >= 1 && expression <= 5)
  }


  test("A LongevityRateCharacteristic should have expression between 30 and 90"){
    val expression: Int = Characteristic(CharacteristicTaxonomy.LONGEVITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).expression

    assert(expression >= 30 && expression <= 90)
  }


  test("A SpeedRateCharacteristic should have expression between 1 and 2"){
    val expression: Int = Characteristic(CharacteristicTaxonomy.SPEED, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).expression

    assert(expression >= 1 && expression <= 2)
  }


}
