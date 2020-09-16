package view

import java.awt._

import javax.swing._
import model.StatisticalData.{StatisticalData, updateStats}

import scala.controller.GeneratorClimateChange
import scala.model.Time
import scala.model.environment.EnvironmentManager
import scala.model.environment.EnvironmentManager.{addSource, evolution}


object ChartGui extends App {
  Time.initialize()
  var environment = GeneratorClimateChange.generateClimate(200, 200, 10).foldLeft(EnvironmentManager(200, 200))(addSource)
  environment = GeneratorClimateChange.generateSeason().foldLeft(environment)(addSource)
  var statisticalData = StatisticalData(Time.time, environment.environment.map)

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

    (0 until 1).foreach(_ => {
      val frame = new JFrame("ScalaBeelity")
      frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
      val tabbedPane = new JTabbedPane

      0 until 30 foreach (_ => {
        Time.increment(10)
        environment = evolution(environment)
        statisticalData = updateStats(environment.environment, statisticalData)
      })

      //println(statisticalData.variationSequence().map(e => e._1 + ", " + e._2.mkString("[", ", ", "]")))

      //TODO: Modificare tipo generico Array[Array[Double]] in Matrix
      val temperature = new HeatmapChart[Array[Array[Double]]]
      val temperatureChart = temperature.createChart(statisticalData.temperatureMatrix())
      temperatureChart.setPreferredSize(new Dimension(410, 50))
      tabbedPane.addTab("Temperature", null, temperatureChart)

      val humidity = new HeatmapChart[Array[Array[Double]]]
      val humidityChart = humidity.createChart(statisticalData.humidityMatrix())
      humidityChart.setPreferredSize(new Dimension(410, 50))
      tabbedPane.addTab("Humidity", null, humidityChart)

      val pressure = new HeatmapChart[Array[Array[Double]]]
      val pressureChart = pressure.createChart(statisticalData.pressureMatrix())
      pressureChart.setPreferredSize(new Dimension(410, 50))
      tabbedPane.addTab("Pressure", null, pressureChart)

      val season = new SeasonalChart[Seq[(String, Array[Array[Double]])]]
      val seasonalChart = season.createChart(
        statisticalData.variationSequence().map(e => (e._1, Array((1 to e._2.length).map(_.toDouble).toArray, e._2)))
      )
      seasonalChart.setPreferredSize(new Dimension(410, 50))
      tabbedPane.addTab("Seasonal Variation Diagram", null, seasonalChart)

      val colonies = new ColoniesChart[Seq[(Dimension, Int)]]
      val coloniesChart = colonies.createChart(Seq.empty[(Dimension, Int)])
      coloniesChart.setPreferredSize(new Dimension(200, 200))
      tabbedPane.addTab("Colonies", null, coloniesChart)

      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT)

      val gameBar = new JPanel()
      val playButton = new JButton("Play")
      val pauseButton = new JButton("Pause")
      val stopButton = new JButton("Accelerate")
      gameBar.add(playButton)
      gameBar.add(pauseButton)
      gameBar.add(stopButton)

      //Add content to the window.
      frame.add(gameBar, BorderLayout.PAGE_START)
      //frame.setResizable(false)
      frame.setDefaultCloseOperation(3)
      //Add content to the window.
      frame.add(tabbedPane, BorderLayout.CENTER)
      //Display the window.
      frame.pack()
      frame.setVisible(true)
    })
  }


  //The following line enables to use scrolling tabs.

  SwingUtilities.invokeLater(new Runnable() {
    override def run(): Unit = { //Turn off metal's use of bold fonts
      UIManager.put("swing.boldMetal", false)
      createAndShowGUI()
    }
  })
}
