package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Influence.Influence

class InfluenceTest extends AnyFunSuite{
  val influence: Influence = Influence()
  val negativeInfluence: Influence = Influence(typeOfInfluence = InfluenceType.NEGATIVE)

  private val influenceStr = "An Influence should"

  test(s"$influenceStr have default parameters"){
    assert(influence.influenceValue.toInt == 1 && influence.typeOfInfluence.equals(InfluenceType.POSITIVE))
  }

  test(s"$influenceStr raise IllegalArgumentException if % doesn't belong to range (0, 100)"){
    assertThrows[IllegalArgumentException](Influence(influenceInPercentage = -1))
  }

  test(s"$influenceStr have a value between -1 and 1"){
    assert(influence.influenceValue >= -1.0 && influence.influenceValue <= 1.0)
  }

  test(s"$influenceStr return a positive InfluenceValue if InfluenceType is positive"){
    assert(influence.influenceValue >= 0)
  }

  test(s"$influenceStr should return a negative InfluenceValue if InfluenceType is negative"){
    assert(negativeInfluence.influenceValue <= 0)
  }
}
