package scala.model

import scala.model.Statistic.Statistic
import scala.model.StatisticalData.{StatisticalData, updateStats}
import scala.model.bees.bee.Colony.{Colony, Color}
import scala.model.bees.phenotype.Characteristic.Characteristic
import scala.model.bees.phenotype.CharacteristicTaxonomy
import scala.model.environment.Cell
import scala.utility.Point

trait Model {

  def time(): Int

  def update(): Unit

  def statisticalData(): StatisticalData

  def statisticList(): List[(Color, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])]

  def colonies: List[(Point, Int, Double)]

  def temperatureMatrix(): Array[Array[Double]]

  def humidityMatrix(): Array[Array[Double]]

  def pressureMatrix(): Array[Array[Double]]
}

class ModelImpl(numColonies: Int, updateTime: Int, dimension: Int) extends Model {


  Time.initialize()
  Time.setIncrementValue(updateTime)
  private var _statisticalData: StatisticalData = StatisticalData()
  private var _statisticList: List[(Color, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])] = List.empty
  private val ecosystem = new Ecosystem(numColonies, dimension, dimension)

  override def update(): Unit = {
    ecosystem.update()
    _statisticalData = updateStats(ecosystem.environmentManager.environment, ecosystem.colonies, _statisticalData)
    _statisticList = statisticList()
    println(_statisticList)
    Time.increment()
  }

  override def time(): Int = Time.time

  override def statisticalData(): StatisticalData = _statisticalData

  override def statisticList(): List[(Color, Set[(CharacteristicTaxonomy.Value, Characteristic#Expression)])] = {
    ecosystem.colonies.map(c => (c.color, Statistic(c).stat()))
  }

  override def colonies: List[(Point, Int, Double)] = ecosystem.colonies.map(c => (c.position, c.dimension, c.color))
  def colors: Seq[Double] = ecosystem.colonies.map(_.color)

  override def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.numericRepresentation())

  override def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.numericRepresentation())

  override def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.numericRepresentation())

  private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] = {
    val env = ecosystem.environmentManager.environment.map
    env.data.map(strategy).sliding(env cols, env cols) toArray
  }

}
