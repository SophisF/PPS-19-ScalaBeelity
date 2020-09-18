package scala.model

import model.bees.bee.EvolutionManager

import scala.Array.empty

import scala.model.bees.bee.Colony.Colony
import scala.model.environment.property.Property
import scala.model.environment.property.PropertyType._
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.bees.phenotype.CharacteristicTaxonomy.CharacteristicTaxonomy
import scala.model.environment.Environment
import scala.model.bees.phenotype.Characteristic._

object StatisticalData {

  type Range = (Int, Int)

  case class StatisticalData(
    lastUpdate: Int,
    colonies: List[Colony] = List.empty,
    variationSeq: Seq[(PropertyValue[Property], Array[Double])] = properties().map(it => (it, empty))
  ) {
    def variationSequence(): Seq[(String, Array[Double])] = variationSeq.map(elem => (elem._1 toString, elem _2))

    def temperatureRange: List[Range] =  this expression CharacteristicTaxonomy.TEMPERATURE_COMPATIBILITY map toTuple

    def pressureRange: List[Range] = this expression CharacteristicTaxonomy.PRESSURE_COMPATIBILITY map toTuple

    def humidityRange: List[Range] = this expression CharacteristicTaxonomy.HUMIDITY_COMPATIBILITY map toTuple

    def averageAggression: List[Int] = this expression CharacteristicTaxonomy.AGGRESSION_RATE map toInt

    def averageReproduction: List[Int] = this expression CharacteristicTaxonomy.REPRODUCTION_RATE map toInt

    def averageSpeed: List[Int] = this expression CharacteristicTaxonomy.SPEED map toInt

    def averageLongevity: List[Int] = this expression CharacteristicTaxonomy.LONGEVITY map toInt

    private def expression(characteristicTaxonomy: CharacteristicTaxonomy): List[Characteristic#Expression] = {
      colonies.map(c => EvolutionManager.calculateAveragePhenotype(List(c.queen) ++ c.bees)
        .expressionOf(characteristicTaxonomy))
    }
  }

  def updateStats(environment: Environment, colonies: List[Colony], statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    /*case time if Time.time - time > 30 =>
      val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.time, colonies, statistics.variationSeq
        .map(el => (el._1, el._2.appended(toPercentage(el._1, sumCell.get(el._1) / environment.map.data.length)))
        ))*/
    case _ => StatisticalData(statistics.lastUpdate, colonies, statistics.variationSeq)
  }
}
/*

  def updateStats(environment: Environment, statistics: StatisticalData): StatisticalData = statistics.lastUpdate match {
    case time if compare(Time.now(), delay(30, time)) > 0 => StatisticalData(statistics.lastUpdate, environment.map, statistics.variationSeq)
      /*val sumCell = environment.map.data.reduce((a, b) => Cell(a.temperature + b.temperature, a.humidity + b.humidity, a.pressure + b.pressure))
      StatisticalData(Time.now(), environment.map, statistics.variationSeq
        .map(el => (el._1, el._2.appended(toPercentage(el._1, sumCell.get(el._1) / environment.map.data.length)))
        ))*/
    case _ => StatisticalData(statistics.lastUpdate, environment.map, statistics.variationSeq)
  }
}*/
