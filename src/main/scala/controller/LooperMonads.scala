//package scala.controller
//
//import scala.controller.IO.unit
//import scala.model.Time
//import scala.model.environment.EnvironmentManager.{addSource, evolution}
//import scala.model.environment.matrix.Matrix._
//import scala.model.environment.property.Property
//import scala.model.environment.property.Property.toPercentage
//import scala.model.environment.property.PropertySource.SeasonalPropertySource
//import scala.model.environment.{Environment, EnvironmentManager}
//import scala.view.View
//
///**
// * Simply controller of the test
// *
// * @author Paolo Baldini
// */
object LooperMonads {
//  var environmentManager = EnvironmentManager(environmentSize._1, environmentSize._2)
//
//  def game: IO[Unit] = for {
//
//  } yield Unit
//
//  def set(environmentSize: (Int, Int), iterations: Int, updateStep: Int): Unit = {
//    var environmentManager = EnvironmentManager(environmentSize._1, environmentSize._2)
//
//    plot(environmentManager.environment)
//
//    environmentManager = GeneratorClimateChange.randomContinuousFilters(environmentSize._1, environmentSize._2, iterations, 5)
//      .foldLeft(environmentManager)(addSource)
//
//    //    GeneratorClimateChange.generateClimate(environmentSize._1, environmentSize._2, iterations, 5)
//    //      .filter(_ != Option.empty).map(x =>  addSource(environmentManager, x))
//
//
//    environmentManager = addSource(environmentManager, SeasonalPropertySource(Property.Humidity))
//
//    )
//
//    plot(environmentManager.environment)
//  }
//
//  def loop: IO[Unit] = if( iterations == Time.time) unit(Unit) else for {
//
//    _ <- unit(environmentManager.evolution(environmentManager))
//    - <- unit(Time increment 1)
//
//  } yield Unit
//
//
//  private def plot(environment: Environment): Unit = Property.values.foreach(property => View.plot(
//    environment.map.dropColumns(0.5).dropRows(0.5).mapValues(c => toPercentage(property, c get property) toDouble),
//    Property.range(property).minValue,
//    Property.range(property).maxValue,
//    s"${property.toString} (${Property.range(property).minValue}, ${Property.range(property).maxValue})"
//  ))
}
