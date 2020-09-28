package scala.model.bees.bee.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.utility.Point

class CollisionManagerTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()

  private val colony1 = UtilityColonyCreator.createColony(genotype, Point(3, 3))
  private val colony2 = UtilityColonyCreator.createColony(genotype, Point(5, 5))
  private val colony3 = UtilityColonyCreator.createColony(genotype, Point(9, 9))

  test("Two colonies should collide if they are overlapped") {
    assert((CollisionManager collisionAreaBetween) (colony1, colony2) > 0)
  }

  test("The collision area between two colonies should be the number of cells they are overlapped") {
    assert((CollisionManager collisionAreaBetween) (colony1, colony2) == 1)
  }

  test("Two colonies should not collide if they aren't overlapped") {
    assert((CollisionManager collisionAreaBetween) (colony1, colony3) == 0)
  }
}
