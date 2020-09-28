package scala.model.environment

import org.scalatest.funsuite.AnyFunSuite

import scala.model.Time._
import scala.model.environment.property.TimeDependentProperty
import scala.model.environment.property.realization.HumidityProperty
import scala.model.environment.property.source.ContinuousSource

class ClimateManagerTest extends AnyFunSuite {
  private val C = "Climate Manager"

  test(s"$C should generate a season for each Property except Pressure (not a seasonal one).") {
    assert(ClimateManager.generateSeason().distinct.length == 2)
  }

  test(s"$C should have a correct value for iterations") {
    assert(
      ClimateManager.generateLocalChanges((20, 20), 5).map(_.asInstanceOf[ContinuousSource[TimeDependentProperty]])
        .forall(_.daysDuration == 5)
    )
  }

  test(s"$C should have a correct value for firetime") {
    assert(
      ClimateManager.generateLocalChanges((20, 20), 5).map(_.asInstanceOf[ContinuousSource[TimeDependentProperty]])
        .forall(t => daysFrom(t.fireTime) == dayTime())
    )
  }

  test(s"$C should generate a random continuous filter whose center is in environment matrix.") {
    assert(
      ClimateManager.generateLocalChanges((20, 20), 5).map(_.asInstanceOf[ContinuousSource[TimeDependentProperty]])
        .forall(t => daysFrom(t.fireTime) == dayTime())
    )
  }

  test(s"$C should generate a random instantaneous filter whose center is in environment matrix.") {
    assert(Iterator.continually(ClimateManager.randomInstantaneousFilter(HumidityProperty, (20, 20))).take(100000)
      .forall(t => (t.x >= 0 && t.x < 20) && (t.y >= 0 && t.y < 20)))
  }
}
