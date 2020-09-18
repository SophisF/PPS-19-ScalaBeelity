package scala.model.adapter

case class Cell(temperature: Int = 0, humidity: Int = 0, pressure: Int = 0)

object Cell {
  type OldCell = scala.model.environment.Cell

  implicit def toCell(old: OldCell): Cell = Cell(
    old.temperature.numericRepresentation(false),
    old.humidity.numericRepresentation(false),
    old.pressure.numericRepresentation(false)
  )
}
