package view

import java.awt.{BorderLayout, Component, Dimension, Toolkit}

import javax.swing._

import scala.controller.Controller
import scala.model.Time
import scala.utility.Point

class ChartViewImpl(controller: Controller) {
  private type Matrix = Array[Array[Double]]

  val frame = new JFrame()
  frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
  val tabbedPane = new JTabbedPane
  val gameBar = new JPanel()
  val timeLabel = new JLabel()

  def createAndShowGUI(): Unit = {

    controller.properties.foreach(p => tabbedPane.addTab(p._1, null, heatmapChart(p._2)))

    tabbedPane.addChangeListener(_ => updateGui())

    val season = new SeasonalChart[Seq[(String, Array[Array[Double]])]]
    val seasonalChart = season.createChart(
      controller.statisticalData.variationSequence().map(e => (e._1, Array((1 to e._2.length).map(_.toDouble).toArray, e._2)))
    )
    seasonalChart.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Seasonal Variation", null, seasonalChart)

    val colonies = new ColoniesChart[Seq[(Point, Int, Double)]](controller)
    val coloniesChart = colonies.createChart(controller.colonies)
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
    timeLabel.setText("Execution Time: " + Time.time)
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

  val tabsComponents: Map[String, () => Component] = Map(
    "Temperature" -> (() => heatmapChart(controller.properties("Temperature"))),
    "Humidity" -> (() => heatmapChart(controller.properties("Humidity"))),
    "Pressure" -> (() => heatmapChart(controller.properties("Pressure"))),
    "Seasonal Variation" -> (() => new SeasonalChart[Seq[(String, Matrix)]]
      .createChart(controller.statisticalData.variationSequence()
        .map(e => (e._1, Array((1 to e._2.length).map(_.toDouble).toArray, e._2))))
      ),
    "Colonies" -> (() => new ColoniesChart[Seq[(Point, Int, Double)]](controller).createChart(controller.colonies))
  )

  def updateGui(): Unit = SwingUtilities.invokeLater(() => {
    val index = tabbedPane.getSelectedIndex
    if (index >= 0) tabbedPane.setComponentAt(index, tabsComponents(tabbedPane.getTitleAt(index))())

    timeLabel.setText("Execution Time: " + Time.time)
    gameBar.add(timeLabel)
  })

  private def heatmapChart(matrix: Matrix): Component = {
    val heatmap = new HeatmapChart[Array[Array[Double]]]
    val chart = heatmap.createChart(matrix)
    chart
  }
}
