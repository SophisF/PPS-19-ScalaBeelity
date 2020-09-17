package scala.model.adapter

trait Cell {
  def temperature: Int
  def humidity: Int
  def pressure: Int
}

object Cell {
  type OldCell = scala.model.environment.Cell
  implicit def toCell(old: OldCell): Cell = new Cell {
    override def temperature: Int = old.temperature.numericRepresentation(false)

    override def humidity: Int = old.humidity.numericRepresentation(false)

    override def pressure: Int = old.pressure.numericRepresentation(false)
  }
}
