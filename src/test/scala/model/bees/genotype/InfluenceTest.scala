package scala.model.bees.genotype

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Influence.Influence

class InfluenceTest extends AnyFunSuite{
  val influence: Influence = Influence()
  val negativeInfluence: Influence = Influence(typeOfInfluence = InfluenceType.NEGATIVE)

  private val anInfluenceShould = "An Influence should"

  test(s"$anInfluenceShould have default parameters"){
    assert(influence.influenceValue.toInt == 1 && influence.typeOfInfluence.equals(InfluenceType.POSITIVE))
  }

  test(s"$anInfluenceShould raise IllegalArgumentException if % doesn't belong to range (0, 100)"){
    assertThrows[IllegalArgumentException](Influence(influenceInPercentage = -1))
  }

  test(s"$anInfluenceShould raise NoSuchElementException if influence doesn't belong to InfluenceType"){
    assertThrows[NoSuchElementException](InfluenceType(InfluenceType.maxId+1))
  }

  test(s"$anInfluenceShould have a value between -1 and 1"){
    assert(influence.influenceValue >= -1.0 && influence.influenceValue <= 1.0)
  }

  test(s"$anInfluenceShould return a positive InfluenceValue if InfluenceType is positive"){
    assert(influence.influenceValue >= 0)
  }

  test(s"$anInfluenceShould should return a negative InfluenceValue if InfluenceType is negative"){
    assert(negativeInfluence.influenceValue <= 0)
  }
}
