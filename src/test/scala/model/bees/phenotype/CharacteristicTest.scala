package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene
import scala.model.bees.phenotype.Characteristic._
import scala.util.Random


class CharacteristicTest extends AnyFunSuite{
  private val temperatureCharacteristic = Characteristic(CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).asInstanceOf[Characteristic with RangeExpression]
  private val pressureCharacteristic = Characteristic(CharacteristicTaxonomy.PRESSURE_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).asInstanceOf[Characteristic with RangeExpression]
  private val humidityCharacteristic = Characteristic(CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY, 1 + Random.nextDouble() * (Gene.maxFrequency-1)).asInstanceOf[Characteristic with RangeExpression]

  test("An environmental characteristic should have a Range expression"){
    assert(temperatureCharacteristic.expression.isInstanceOf[(Int, Int)] &&
    pressureCharacteristic.expression.isInstanceOf[(Int, Int)] &&
    humidityCharacteristic.expression.isInstanceOf[(Int, Int)])
  }

  // Test also for ExpressionMapper
  test("A TemperatureCompatibilityCharacteristic should have expression between 18 and 36"){
    val range: (Int, Int) = temperatureCharacteristic.expression
    println("Temperature: (" + range._1 +", " + range._2 + ")")
    assert(range._1 >= TemperatureCharacteristicObj.min && range._2 <= TemperatureCharacteristicObj.max)
  }

  test("A humidityCompatibilityCharacteristic should have expression between 40 and 70"){
    val range: (Int, Int) = humidityCharacteristic.expression
    println("Humidity: (" + range._1 +", " + range._2 + ")")
    assert(range._1 >= HumidityCharacteristicObj.min && range._2 <= HumidityCharacteristicObj.max)
  }

  test("A pressureCompatibilityCharacteristic should have expression between 1000 and 1050"){
    val range: (Int, Int) = pressureCharacteristic.expression
    println("Pressure: (" + range._1 +", " + range._2 + ")")
    assert(range._1 >= PressureCharacteristicObj.min && range._2 <= PressureCharacteristicObj.max)
  }

}
