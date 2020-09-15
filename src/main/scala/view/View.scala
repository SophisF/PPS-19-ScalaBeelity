package scala.view

import java.awt.{BorderLayout, Dimension, Toolkit}

import javax.swing.{JButton, JFrame, JLabel, JPanel, JTabbedPane, SwingUtilities}

import model.StatisticalData.StatisticalData

trait View {

  def createAndShowGUI(statisticalData: StatisticalData, time: Int)

  def updateGui(statisticalData: StatisticalData, time: Int)

}

object View extends View {

  val frame = new JFrame()
  frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
  val tabbedPane = new JTabbedPane
  val gameBar = new JPanel()
  val timeLabel = new JLabel()


  override def createAndShowGUI(statisticalData: StatisticalData, time: Int): Unit = {

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

    val playButton = new JButton("Play")
    val pauseButton = new JButton("Pause")
    val stopButton = new JButton("Accelerate")
    playButton.addActionListener((e) => {
      //TODO:Implement action listener
    })
    pauseButton.addActionListener((e) => {
      //TODO:Implement action listener
    })
    stopButton.addActionListener((e) => {
      //TODO:Implement action listener
    })
    timeLabel.setText("Execution Time: " + time)
    gameBar.add(playButton)
    gameBar.add(pauseButton)
    gameBar.add(stopButton)
    gameBar.add(timeLabel)

    frame.add(gameBar, BorderLayout.PAGE_START)
    frame.setDefaultCloseOperation(3)
    frame.add(tabbedPane, BorderLayout.CENTER)
    frame.pack()
    frame.setVisible(true)

  }

  override def updateGui(statisticalData: StatisticalData, time: Int): Unit = {
    SwingUtilities.invokeLater(new Runnable() {
      override def run(): Unit = {
        timeLabel.setText("Execution Time: " + time)
        gameBar.add(timeLabel)
        tabbedPane.getSelectedIndex match {
          case 0 =>
            val temperature = new HeatmapChart[Array[Array[Double]]]
            val temperatureChart = temperature.createChart(statisticalData.temperatureMatrix())
            temperatureChart.setPreferredSize(new Dimension(410, 50))
            tabbedPane.setComponentAt(tabbedPane.getSelectedIndex, temperatureChart)
          case 1 =>
            val humidity = new HeatmapChart[Array[Array[Double]]]
            val humidityChart = humidity.createChart(statisticalData.humidityMatrix())
            humidityChart.setPreferredSize(new Dimension(410, 50))
            tabbedPane.setComponentAt(tabbedPane.getSelectedIndex, humidityChart)
          case 2 =>
            val pressure = new HeatmapChart[Array[Array[Double]]]
            val pressureChart = pressure.createChart(statisticalData.pressureMatrix())
            pressureChart.setPreferredSize(new Dimension(410, 50))
            tabbedPane.setComponentAt(tabbedPane.getSelectedIndex, pressureChart)
          case 3 =>
            val season = new SeasonalChart[Seq[(String, Array[Array[Double]])]]
            val seasonalChart = season.createChart(
              statisticalData.variationSequence().map(e => (e._1, Array((1 to e._2.length).map(_.toDouble).toArray, e._2)))
            )
            seasonalChart.setPreferredSize(new Dimension(410, 50))
            tabbedPane.setComponentAt(tabbedPane.getSelectedIndex, seasonalChart)
          case 4 =>
            val colonies = new ColoniesChart[Seq[(Dimension, Int)]]
            val coloniesChart = colonies.createChart(Seq.empty[(Dimension, Int)])
            coloniesChart.setPreferredSize(new Dimension(200, 200))
            tabbedPane.setComponentAt(tabbedPane.getSelectedIndex, coloniesChart)
        }
      }
    })
  }
}
