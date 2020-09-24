package model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.{Bee, CollisionManager, Colony, Queen}
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype.Phenotype
import scala.util.Random
import scala.utility.Point

class CollisionManagerTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()
  private val phenotype: Phenotype = genotype expressItself

  private val age = 0
  private val t = 20
  private val p = 1000
  private val h = 50

  private val colony1 = Colony(Random.nextDouble(), Queen(None, genotype, phenotype, age, t, p, h, Point(3, 3), null),
    (1 to 10).map(_ => Bee(genotype, phenotype, age, t, p, h)).toSet)
  private val colony2 = Colony(Random.nextDouble(), Queen(None, genotype, phenotype, age, t, p, h, Point(5, 5), null),
    (1 to 10).map(_ => Bee(genotype, phenotype, age, t, p, h)).toSet)
  private val colony3 = Colony(Random.nextDouble(), Queen(None, genotype, phenotype, age, t, p, h, Point(5, 9), null),
    (1 to 10).map(_ => Bee(genotype, phenotype, age, t, p, h)).toSet)

  test("Two colonies should collide if they are overlapped") {
    assert(CollisionManager.collisionArea(colony1, colony2) > 0)
  }

  test("The collision area between two colonies should be the number of cells they are overlapped") {
    println(CollisionManager.collisionArea(colony1, colony2))
    assert(CollisionManager.collisionArea(colony1, colony2) == 1)
  }

  test("Two colonies should not collide if they aren't overlapped") {
    assert(CollisionManager.collisionArea(colony1, colony3) == 0)
  }
}
