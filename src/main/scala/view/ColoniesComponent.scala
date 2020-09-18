package view

import java.awt.{Canvas, Color, Graphics, Toolkit}

import scala.utility.Point

class ColoniesComponent[T <: Seq[(Point, Int, Double)]](dimension: (Int, Int), data: T) extends Canvas {

  val gameBar = 100
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize
  //println(screenSize.width + " " + screenSize.height)

  private val MULTIPLIER = Math.min(screenSize.width / dimension._1, (screenSize.height - gameBar) / dimension._2) - 1
  //println(MULTIPLIER)
  val initialX: Int = 0
  val initialY: Int = 0


  //TODO: Impostare dimensione oggetti a partire dal centro meno la dimenzion
  override def paint(g: Graphics): Unit = {
    //g.setClip(0, 0, 300, 300)
    setBackground(Color.WHITE)
    //setPreferredSize(new Dimension(200, 200))
    setForeground(Color.BLACK)
    g.drawRect(initialX, initialY, dimension._1 * MULTIPLIER, dimension._2 * MULTIPLIER)


    data.foreach(d => {
      g.setColor(Color.getHSBColor(d._3.toFloat, 1, 1))
      g.fillRect((initialX + d._1.x - d._2) * MULTIPLIER, (initialY + d._1.y - d._2 )* MULTIPLIER, d._2 * MULTIPLIER, d._2 * MULTIPLIER)
    })
    //    g.fillRect(initialX + 3 * MULTIPLIER, initialY + 3 * MULTIPLIER, 40 * MULTIPLIER, 40 * MULTIPLIER)
    //    g.fillRect(initialX + 200 * MULTIPLIER, initialY + 0 * MULTIPLIER, 23 * MULTIPLIER, 23 * MULTIPLIER)
  }
}