package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Influence.Influence

class InfluenceTest extends AnyFunSuite{
  val influence: Influence = Influence()
  val negativeInfluence: Influence = Influence(typeOfInfluence = InfluenceType.NEGATIVE)

  test("An Influence should have default parameters"){
    assert(influence.influenceValue == 1.0 && influence.typeOfInfluence.equals(InfluenceType.POSITIVE))
  }

  test("An Influence should raise IllegalArgumentException if % doesn't belong to range (0, 100)"){
    assertThrows[IllegalArgumentException](Influence(influenceInPercentage = -1))
  }

  test("An Influence should raise NoSuchElementException if influence doesn't belong to InfluenceType"){
    assertThrows[NoSuchElementException](InfluenceType(InfluenceType.maxId+1))
  }

  test("An Influence should return a positive InfluenceValue if InfluenceType is positive"){
    assert(influence.influenceValue >= 0)
  }

  test("An Influence should return a negative InfluenceValue if InfluenceType is negative"){
    assert(negativeInfluence.influenceValue <= 0)
  }
}
