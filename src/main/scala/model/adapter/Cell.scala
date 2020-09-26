package scala.model.adapter
// TODO move to environment to made old cell type package protected
/**
 * An object structure to share between environment and colonies
 *
 * @param temperature of the environment's cell in a numeric format
 * @param humidity of the environment's cell in a numeric format
 * @param pressure of the environment's cell in a numeric format
 */
case class Cell(temperature: Int = 0, humidity: Int = 0, pressure: Int = 0)

object Cell {
  type OldCell = scala.model.environment.Cell

  /**
   * Convert an old-type cell to this one
   *
   * @param old type of cell
   * @return this container cell
   */
  implicit def toCell(old: OldCell): Cell = Cell(
    old.temperature.numericRepresentation(false),
    old.humidity.numericRepresentation(false),
    old.pressure.numericRepresentation(false)
  )
}
