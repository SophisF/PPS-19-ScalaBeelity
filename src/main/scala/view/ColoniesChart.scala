package scala.view

import java.awt.{BorderLayout, Color, Component}

import javax.swing.{JLabel, JPanel}

import scala.controller.Controller
import scala.utility.Point


class ColoniesChart[T <: Seq[(Point, Int)]](controller: Controller) extends Chart[T] {


  override def createChart(data: T): Component = {
    val panel = new JPanel()
    panel.setLayout(new BorderLayout())
    //    //panel.setSize(new Dimension(100,200))

    //    val statistics = new JTextArea()
    //    statistics.setSize(500, 500)
    //    statistics.setEditable(false)

    //TODO: da provare

    val label = new JPanel()


    label.add(new JLabel("Aggresivity"))
    controller.statisticalData.averageAggression.zip(controller.color).foreach(d => {
      val l = new JLabel(d._1.toString)
      l.setForeground(Color.getHSBColor(d._2.toFloat, 1, 1))
      label.add(l)
    })
    panel.add(label, BorderLayout.EAST)

    val colonies = new ColoniesComponentPanel(controller, data zip controller.color)
    colonies.setSize(500, 500)
    panel.add(colonies, BorderLayout.CENTER)
    //    panel.setForeground(Color.BLACK)
    //    panel.setVisible(true)
    //    println(panel.getWidth + " " + panel.getHeight)
    //
    //    panel.repaint()
    //    colonies.repaint()
    //
    panel

  }

}

