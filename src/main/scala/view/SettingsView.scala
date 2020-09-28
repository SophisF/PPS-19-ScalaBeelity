package scala.view

import java.awt.GridLayout

import javax.swing.JOptionPane._
import javax.swing._

/**
 * View for setup simulation.
 */
object SettingsView {
  private val MatrixSize = Array("100", "200", "300")
  private val Iterations = Array("1000", "5000", "infinite")
  private val DefaultColoniesNumber = 1
  private val DefaultTemporalGranularity = 1

  def createAndShow: Option[SimulationSettings] = {
    val panel = new JPanel(new GridLayout(0, 1))

    panel.add(new JLabel("N° Colonies (queens):"))
    val numColonies = new JTextField(DefaultColoniesNumber toString)
    panel.add(numColonies)

    panel.add(new JLabel("Temporal Granularity :"))
    val temporalGranularity = new JTextField(DefaultTemporalGranularity toString)
    panel.add(temporalGranularity)

    panel.add(new JLabel("N° max iterations:"))
    val comboIterations = new JComboBox(Iterations)
    panel.add(comboIterations)

    panel.add(new JLabel("Environment size:"))
    val comboMatrix = new JComboBox(MatrixSize)
    panel.add(comboMatrix)

    showConfirmDialog(null, panel, "Test", OK_CANCEL_OPTION, PLAIN_MESSAGE) match {
      case OK_OPTION => Option(SimulationSettings(numColonies, temporalGranularity,
        comboIterations.getSelectedItem match {
          case "infinite" => -1
          case value => value.toString.toInt
        }, (comboMatrix, comboMatrix)))
      case _ => Option.empty
    }
  }

  private implicit def numberFrom(component: JTextField): Int = component.getText toInt

  private implicit def numberFrom[T](component: JComboBox[T]): Int = component.getSelectedItem.toString toInt
}
