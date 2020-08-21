package scala.model.bees.phenotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Gene
import scala.model.bees.phenotype.Characteristic.{Characteristic, RangeExpression, TemperatureCompatibilityCharacteristic}
import scala.model.bees.phenotype.ExpressionMapper._
import scala.util.Random

class CharacteristicTest extends AnyFunSuite{
  private val environmentalCharacteristic = TemperatureCompatibilityCharacteristic(1 + Random.nextDouble() * Gene.maxFrequency - 1)


  test("An environmental characteristic should have a Range expression"){
    assert(environmentalCharacteristic.expression.isInstanceOf[Characteristic with RangeExpression])
  }

  test("A TemperatureCompatibilityCharacteristic should have expression between 18 and 36"){
    val range: (Int, Int) = environmentalCharacteristic.expression
    assert(range._1 >= 18 && range._2 <= 36)
  }
}
