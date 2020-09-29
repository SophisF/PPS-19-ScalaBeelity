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
    assert(!(env2.cells() equals evolution(env2).cells()))
  }

  test(s"$E should change after 10 iteration.") {
    Time.reset()
    val env = EnvironmentManager(20, 20)
    Time.increment(5)
    assert(!(env.cells() equals evolution(env).cells()))
  }

}
