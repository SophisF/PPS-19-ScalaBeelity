package scala.view

import java.awt.GridLayout

import javax.swing._


class StartViewImpl {
  //  val DIMENSION = 200
  //  val frame = new JFrame("ScalaBeelity")
  //  frame.setPreferredSize(new Dimension(DIMENSION, DIMENSION))


  def createAndShowGUI(callback: (Int, Int, Int, Int) => Unit): Unit = {

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
    val result = JOptionPane.showConfirmDialog(null, panel, "Test", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
    if (result == JOptionPane.OK_OPTION) {
      //TODO : refactor with enumeration
      callback(numColonies.getText.toInt, temporalGranularity.getText.toInt, comboIter.getSelectedItem match {
        case "infinite" => -1
        case x => x.toString.toInt
      }, comboMatrix.getSelectedItem.toString.toInt)
      //System.out.println(comboIter.getSelectedItem + " " + numColonies.getText + " " + temporalGranularity.getText)
    } else System.out.println("Cancelled")
    //    frame.add(panel, BorderLayout.CENTER)
    //    frame.pack()
    //    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    //    frame.setVisible(true)

  }
}
