package scala.model.environment

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.realization.HumidityProperty
import scala.model.environment.property.source.ZoneSource.Source

class EnvironmentTest extends AnyFunSuite {

  test("Apply accepting width should return an environment with an equal number of cols") {
    assert(Environment(5, 10).map.cols == 5)
  }

  test("Apply accepting height should return an environment with an equal number of rows") {
    assert(Environment(10, 5).map.rows == 5)
  }

  test("All cells should be equals to default one at the creation") {
    assert(Environment(5, 5).map.data.forall(Cell.equals(_, Cell())))
  }
}
