package scala.model

import scala.model.property.PropertySource.ContinuousPropertySource

object EnvironmentManager {

  case class EnvironmentManager(environment: Environment, time: Int, continuousFilters: ContinuousPropertySource*)

/*  def apply(): EnvironmentManager = new EnvironmentManager(dvdf, ddfv, Iterator.continually(ContinuousPropertySource(1,
    (evolvableData, time) => {

  },3,4,5,6, 7, 8,)))

  def evolution(actualTime: Int): Unit = {

  }*/
}
