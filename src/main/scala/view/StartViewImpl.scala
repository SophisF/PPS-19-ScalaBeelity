package scala.view

import java.awt.{BorderLayout, Dimension, GridLayout}

import javax.swing.{JComboBox, JFrame, JLabel, JOptionPane, JPanel, JTextField, WindowConstants}

object StartViewImpl /*extends App*/ {

  val DIMENSION = 200
  val frame = new JFrame("ScalaBeelity")
  frame.setPreferredSize(new Dimension(DIMENSION, DIMENSION))


  def createAndShowGUI(): Unit = {

    val matrixDim = Array("100", "200", "300")
    val comboMatrix = new JComboBox(matrixDim)
    val iterations = Array("1000", "5000", "infinite")
    val comboIter = new JComboBox(iterations)

    val field1 = new JTextField()
    val field2 = new JTextField()
    val panel = new JPanel(new GridLayout(0, 1))
    panel.add(new JLabel("N° Colonies (queens):"))
    panel.add(field1)
    panel.add(new JLabel("Temporal Granularity :"))
    panel.add(field2)
    panel.add(new JLabel("N° max iterations:"))
    panel.add(comboIter)
    panel.add(new JLabel("Dim of Matrix:"))
    panel.add(comboMatrix)
    val result = JOptionPane.showConfirmDialog(null, panel, "Test", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
    if (result == JOptionPane.OK_OPTION) System.out.println(comboIter.getSelectedItem + " " + field1.getText + " " + field2.getText)
    else System.out.println("Cancelled")
    frame.add(panel, BorderLayout.CENTER)
    frame.pack()
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setVisible(true)

  }

//  SwingUtilities.invokeLater(new Runnable() {
//    override def run(): Unit = { //Turn off metal's use of bold fonts
//      UIManager.put("swing.boldMetal", false)
//      createAndShowGUI()
//    }
//  })

}
