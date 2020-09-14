package view

import java.awt._
import java.awt.event.KeyEvent

import javax.swing._

import scala.model.environment.EnvironmentManager


object SwingGui extends App {
  var environment = EnvironmentManager(20, 20)


  // println(environment.environment.map.data.
  //     map(it => it.temperature.toDouble).
  //      sliding(20, 20).toArray.map(_.mkString("", " ", "")).mkString("", "\n", ""))

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
      environment.environment.map.data.
        map(it => it.temperature.toDouble).
        sliding(environment.environment.map.cols, environment.environment.map.cols).toArray,
      (0 to environment.environment.map.cols).map(_.toDouble).toArray,
      (0 to environment.environment.map.rows).map(_.toDouble).toArray
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
    val gameBar = new JPanel()
    val playButton = new JButton("Play")
    val pauseButton = new JButton("Pause")
    val stopButton = new JButton("Accelerate")
    gameBar.add(playButton)
    gameBar.add(pauseButton)
    gameBar.add(stopButton)

    //Add content to the window.
    frame.add(gameBar, BorderLayout.PAGE_START)

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
