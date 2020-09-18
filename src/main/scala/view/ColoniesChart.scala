package view

import java.awt.Component

import scala.controller.Controller
import scala.utility.Point


class ColoniesChart[T <: Seq[(Point, Int, Double)]](controller: Controller) extends Chart[T] {


  override def createChart(data: T): Component = {
    //    val panel = new JPanel()
    //    //panel.setSize(new Dimension(100,200))
    //    val statistics = new JTextArea()
    //    statistics.setEditable(false)
    //    statistics.append("ADmdsonfosdfnosdfnsodinfosdfnsoifsd")
    //    panel add(new JPanel().add(statistics), BorderLayout.WEST)
    new ColoniesComponent(controller.environmentDimension(), data)
    //    colonies.setSize(500, 500)
    //    panel.add(colonies, BorderLayout.EAST)
    //    panel.setForeground(Color.BLACK)
    //    panel.setVisible(true)
    //    println(panel.getWidth + " " + panel.getHeight)
    //
    //    panel.repaint()
    //    colonies.repaint()
    //
    //    panel

  }

}
