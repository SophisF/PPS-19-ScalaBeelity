package scala.model

import scala.model.StatisticalData.{StatisticalData, updateStats}
import scala.model.environment.Cell
import scala.util.Random
import scala.utility.Point

trait Model {

  def time(): Int

  def update(): Unit

  def statisticalData(): StatisticalData

  def colonies: List[(Point, Int, Double)]

  def temperatureMatrix(): Array[Array[Double]]

  def humidityMatrix(): Array[Array[Double]]

  def pressureMatrix(): Array[Array[Double]]
}

class ModelImpl(numColonies: Int, updateTime: Int, dimension: Int) extends Model {

  Time.initialize()
  Time.setIncrementValue(updateTime)
  private var _statisticalData: StatisticalData = StatisticalData()
  private val ecosystem = new Ecosystem(numColonies, dimension, dimension)

  override def update(): Unit = {
    ecosystem.update()
    _statisticalData = updateStats(ecosystem.environmentManager.environment, ecosystem.colonies, _statisticalData)
    Time.increment()
  }

  override def time(): Int = Time.time

  override def statisticalData(): StatisticalData = _statisticalData

  override def colonies: List[(Point, Int, Double)] = ecosystem.colonies.map(c => (c.position, c.dimension, c.color))

  override def temperatureMatrix(): Array[Array[Double]] = propertyMatrix(_.temperature.numericRepresentation())

  override def humidityMatrix(): Array[Array[Double]] = propertyMatrix(_.humidity.numericRepresentation())

  override def pressureMatrix(): Array[Array[Double]] = propertyMatrix(_.pressure.numericRepresentation())

  private def propertyMatrix(strategy: Cell => Double): Array[Array[Double]] = {
    val env = ecosystem.environmentManager.environment.map
    env.data.map(strategy).sliding(env cols, env cols) toArray
  }


  //TODO: verificare se va bene qui
  
  val diffPosition = 10

  def proximityOf(position: Point): Point = {
    val cells = for {
      i <- position.x - diffPosition to position.x + diffPosition
      j <- position.y - diffPosition to position.y + diffPosition
      if i > 0 && i < ecosystem.environmentManager.environment.map.rows &&
        j > 0 && j < ecosystem.environmentManager.environment.map.cols
    } yield (i, j)
    val index = Random.nextInt(cells.size)
    Point(cells(index)._1, cells(index)._2)
  }

}
