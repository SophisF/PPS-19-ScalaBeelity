package scala.view

import java.awt.{BorderLayout, Color, Component, GridLayout}

import javax.swing.{JLabel, JPanel, JScrollPane}

import scala.controller.Controller
import scala.utility.Point


class ColoniesChart[T <: Seq[(Point, Int)]](controller: Controller) extends Chart[T] {

  def createStatistical(panel: JPanel, title: String, statistics: List[Int]) = {
    panel.add(new JLabel(title + "        "))
    statistics.zip(controller.color).foreach(d => {
      val l = new JLabel(d._1.toString)
      l.setForeground(Color.getHSBColor(d._2.toFloat, 1, 1))
      panel.add(l)
    })
  }


  override def createChart(data: T): Component = {
    val panel = new JPanel()
    panel.setLayout(new BorderLayout())
    //    //panel.setSize(new Dimension(100,200))

    //    val statistics = new JTextArea()
    //    statistics.setSize(500, 500)
    //    statistics.setEditable(false)

    //TODO: da provare

    val scrol = new JScrollPane()
    val label = new JPanel()
    label.setLayout(new GridLayout(4, data.size))


//    createStatistical(label, "Aggr",controller.statisticalData.averageAggression )
//    createStatistical(label, "Long",controller.statisticalData.averageLongevity )
//    createStatistical(label, "Repr",controller.statisticalData.averageReproduction )
//    createStatistical(label, "Speed",controller.statisticalData.averageSpeed )
    scrol.setSize(300,300)
    scrol.add(label)
    panel.add(scrol, BorderLayout.EAST)

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

