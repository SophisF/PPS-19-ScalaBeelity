package scala.view

import java.awt._

import javax.swing.JPanel

import scala.controller.Controller
import scala.utility.Point

class ColoniesComponentPanel[T <: Seq[((Point, Int), Double)]](controller: Controller, data: T) extends JPanel {

  val gameBar = 100
  val screenSize = Toolkit.getDefaultToolkit.getScreenSize
  //println(screenSize.width + " " + screenSize.height)

  val dimension = controller.environmentDimension()
  private val MULTIPLIER = Math.min(screenSize.width / dimension._1, (screenSize.height - gameBar) / dimension._2) - 1
  //println(MULTIPLIER)
  val initialX: Int = 0
  val initialY: Int = 0

  //  val t = new JTextArea()
  //  t.append("Prova")
  //  t.append("Ciaoooooooooooooo")
  //
  //  this.add(t)


  override protected def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val gg = g.asInstanceOf[Graphics2D]
    gg.setColor(Color.BLACK)
    gg.draw(new Rectangle(initialX, initialY, dimension._1 * MULTIPLIER, dimension._2 * MULTIPLIER))

    //gg.draw(new Rectangle(dimension._1 * MULTIPLIER, initialY, dimension._1 * MULTIPLIER, dimension._2 * MULTIPLIER))



    data.foreach(d => {
      gg.setColor(Color.getHSBColor(d._2.toFloat, 1, 1))
      gg.fill(new Rectangle((d._1._1.x - d._1._2) * MULTIPLIER, (d._1._1.y - d._1._2) * MULTIPLIER, d._1._2 * MULTIPLIER, d._1._2 * MULTIPLIER))

      // val s = controller.statisticalData.averageAggression.foreach(e => gg.)
    })
    /*
    val rectangle = new Rectangle(100, 100, 100, 100)
    val rectangle2 = new Rectangle(400, 400, 100, 100)


    gg.setColor(Color.BLUE)
    gg.fill(rectangle)
    gg.setColor(Color.BLACK)
    gg.draw(rectangle)
    gg.setColor(Color.BLUE)
    gg.fill(rectangle2)
    gg.setColor(Color.BLACK)
    gg.draw(rectangle2)

     */
  }
}