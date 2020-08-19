package bees.phenotype

import bees.genotype.Gene
import bees.phenotype.Characteristic.{Characteristic, TemperatureCompatibilityCharacteristic}
import org.scalatest.funsuite.AnyFunSuite
import bees.phenotype.ExpressionMapper._

import scala.util.Random

class CharacteristicTest extends AnyFunSuite{
  private val characteristic: Characteristic = TemperatureCompatibilityCharacteristic(1 + Random.nextDouble() * Gene.maxFrequency - 1)


  test("An environmental characteristic should have a Range expression"){
    assert(characteristic.expression match {
      case (_:Int, _:Int) => true
      case _ => false
    })
  }

  test("A TemperatureCompatibilityCharacteristic should have expression between 18 and 36"){
    val range: (Int, Int) = characteristic.expression.asInstanceOf[(Int, Int)]
    assert(range._1 >= 18 && range._2 <= 36)
  }
}
