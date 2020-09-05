package model.bees.bee

import org.scalatest.funsuite.AnyFunSuite

import scala.model.bees.bee.{CollisionManager, Queen}
import scala.model.bees.genotype.Genotype
import scala.model.bees.genotype.Genotype.Genotype
import scala.model.bees.phenotype.Phenotype
import scala.model.bees.phenotype.Phenotype.Phenotype

class CollisionManagerTest extends AnyFunSuite {
  private val genotype: Genotype = Genotype()
  private val phenotype: Phenotype = Phenotype(Genotype.calculateExpression(genotype))
  private val queen1 = Queen(None, genotype, phenotype, age = 0, temperature = 20, pressure = 1000, humidity = 50, position = (3, 3))
  private val queen2 = Queen(None, genotype, phenotype, age = 0, temperature = 20, pressure = 1000, humidity = 50, position = (5, 5))
  private val queen3 = Queen(None, genotype, phenotype, age = 0, temperature = 20, pressure = 1000, humidity = 50, position = (5, 9))

  test("Two colonies should collide if they are overlapped") {
    assert(CollisionManager.collisionArea(queen1.colony, queen2.colony) > 0)
  }

  test("The collision area between two colonies should be the number of cells they are overlapped") {
    assert(CollisionManager.collisionArea(queen1.colony, queen2.colony) == 1)
  }

  test("Two colonies should not collide if they aren't overlapped") {
    assert(CollisionManager.collisionArea(queen2.colony, queen3.colony) == 0)
  }
}
