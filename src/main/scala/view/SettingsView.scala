package scala.view

import java.awt.GridLayout

import javax.swing.JOptionPane._
import javax.swing._

object SettingsView {

  def createAndShow: Option[SimulationSettings] = {
    val matrixDim = Array("100", "200", "300")
    val comboMatrix = new JComboBox(matrixDim)
    val iterations = Array("1000", "5000", "infinite")
    val comboIter = new JComboBox(iterations)

    val numColonies = new JTextField("1")
    val temporalGranularity = new JTextField("1")
    val panel = new JPanel(new GridLayout(0, 1))
    panel.add(new JLabel("N° Colonies (queens):"))
    panel.add(numColonies)
    panel.add(new JLabel("Temporal Granularity :"))
    panel.add(temporalGranularity)
    panel.add(new JLabel("N° max iterations:"))
    panel.add(comboIter)
    panel.add(new JLabel("Dim of Matrix:"))
    panel.add(comboMatrix)

    showConfirmDialog(null, panel, "Test", OK_CANCEL_OPTION, PLAIN_MESSAGE) match {
      case OK_OPTION => Option(SimulationSettings(numColonies, temporalGranularity, comboIter.getSelectedItem match {
        case "infinite" => -1
        case value => value.toString.toInt
      }, (comboMatrix, comboMatrix)))
      case _ => Option.empty
    }
  }

  private implicit def numberFrom(component: JTextField): Int = component.getText toInt

  private implicit def numberFrom[T](component: JComboBox[T]): Int = component.getSelectedItem.toString toInt
}
