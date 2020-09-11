package view

import java.awt._
import java.awt.event.KeyEvent

import javax.swing._


object SwingGui extends App {


  protected def makeTextPanel(text: String) = {
    val panel = new JPanel(false)
    val filler = new JLabel(text)
    filler.setHorizontalAlignment(0)
    panel.setLayout(new GridLayout(1, 1))
    panel.add(filler)
    panel
  }

  /** ************************************TABBED GUI ******************************************************* */

  //TODO: Da modificare qui
  private def createAndShowGUI(): Unit = { //Create and set up the window.


    val frame = new JFrame("ScalaBeelity")
    frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
    val tabbedPane = new JTabbedPane

    HeatmapChart.createDataset(
      Array(
        Array(1.0, 2.0, 4.0, 1.0),
        Array(6.0, 3.0, 5.0, 2.0),
        Array(42.0, 22.0, 122.0, 52.0),
        Array(5333.0, 433.0, 233.0, 33.0)
      ),
      Array(0.0, 1.0, 2.0, 3.0),
      Array(0.0, 1.0, 2.0, 3.0)
    )
    val temperature = HeatmapChart.createChart()
    temperature.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Temperature", null, temperature)
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1)

    val panel2 = makeTextPanel("Panel #2")
    panel2.setPreferredSize(new Dimension(410, 50))

    tabbedPane.addTab("Humidity", null, panel2)
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2)

    val panel3 = makeTextPanel("Panel #3")
    panel3.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Pressure", null, panel3)
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3)

    val season = SeasonalChart.createChart
    season.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Seasonal Variation Diagram", null, season)
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4)

    val colonies = ColoniesChart
    colonies.setPreferredSize(new Dimension(200, 200))
    tabbedPane.addTab("Colonies", null, colonies)
    tabbedPane.setMnemonicAt(4, KeyEvent.VK_5)

    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT)

    frame.setDefaultCloseOperation(3)
    //Add content to the window.
    frame.add(tabbedPane, BorderLayout.CENTER)
    //Display the window.
    frame.pack()
    frame.setVisible(true)
  }


  //The following line enables to use scrolling tabs.


  SwingUtilities.invokeLater(new Runnable() {
    override def run(): Unit = { //Turn off metal's use of bold fonts
      UIManager.put("swing.boldMetal", false)
      createAndShowGUI()
    }
  })
}
