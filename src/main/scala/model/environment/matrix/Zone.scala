package scala.model.environment.matrix

import scala.model.environment.matrix.Size.Border._
import scala.utility.Point

private[environment] trait Zone extends Point with Size

/**
 * Represent a centered area in a matrix
 *
 */
private[environment] object Zone {

  /**
   * Calculate x/y position of a border. In other words, x or y of cells that reside on the borders of an area.
   *
   * @param zone to which calculate border position
   * @param border left, right, top, bottom
   * @return coordinate of the border in one axis. E.g., left/right on X axis, top/bottom on Y axis
   */
  def border(zone: Zone)(border: Border): Int = border match {
    case Top | Bottom => zone.y + zone ~ border
    case _ => zone.x + zone ~ border
  }

  /**
   * Check if a point is contained into the zone
   *
   * @param x of the point
   * @param y of the point
   * @param zone of which check membership
   * @return true if the point is contained in the zone, false otherwise
   */
  def in(x: Int, y: Int, zone: Zone): Boolean = x >= border(zone)(Left) && x <= border(zone)(Right) &&
    y >= border(zone)(Top) && y <= border(zone)(Bottom)
}
