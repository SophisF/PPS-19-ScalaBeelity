package view

import java.awt.{BorderLayout, Component}

import javax.swing.JPanel

class ColoniesChart[T <: Seq[((Int, Int), Int)]] extends Chart[T] {

  override def createChart(data: T): Component = {
    val panel = new JPanel()
    //    val statistics = new JTextArea()
    //    statistics.setEditable(false)
    //    statistics.append("ADmdsonfosdfnosdfnsodinfosdfnsoifsd")
    //panel add(new JPanel().add(statistics), BorderLayout.WEST)
    panel.add(new ColoniesComponent(data), BorderLayout.CENTER)
    panel.setVisible(true)
    panel
  }

}
