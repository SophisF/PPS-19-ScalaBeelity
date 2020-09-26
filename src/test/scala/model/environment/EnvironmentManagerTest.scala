package scala.model.environment

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time
import scala.model.environment.EnvironmentManager.evolution

class EnvironmentManagerTest extends AnyFunSuite {

  private val E = "Environment Manager"

  test(s"$E should change after 5 iterations.") {
    Time.reset()
    val env2 = EnvironmentManager(30, 30)
    Time.increment(10)
    assert(!(env2.environment.map equals evolution(env2).environment.map))
  }

  test(s"$E should change after 10 iteration.") {
    Time.reset()
    val env = EnvironmentManager(20, 20)
    Time.increment(5)
    assert(!(env.environment.map equals evolution(env).environment.map))
  }

  test(s"$E should give cells matrix, whose represent environment matrix. ") {
    val environment = EnvironmentManager(30, 30)
    assert(environment.environment.map equals environment.cells().map(_.asInstanceOf[Cell]))
  }

}
