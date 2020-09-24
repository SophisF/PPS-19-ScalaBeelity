package scala.view

import java.awt.{BorderLayout, Component, Dimension, Toolkit}

import javax.swing._

import scala.controller.Controller
import scala.model.Time
import scala.view.builder.{ColoniesChartBuilder, HeatmapChartBuilder, SeasonalChartBuilder}

class SimulationView(controller: Controller) {
  private type Matrix = Array[Array[Double]]

  val frame = new JFrame()
  frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
  val tabbedPane = new JTabbedPane
  val gameBar = new JPanel()
  val timeLabel = new JLabel()

  def createAndShow(): Unit = {
    controller.properties.foreach(p => tabbedPane.addTab(p._1, null, heatmapChart(p._2)))

    tabbedPane.addChangeListener(_ => update())

    val seasonalChart = SeasonalChartBuilder.createChart(controller.statisticEnvironment.variationSequence()
      .map(e => (e._1, Array((1 to e._2.size).map(_ toDouble).toArray, e._2.toArray))))
    seasonalChart.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Seasonal Variation", null, seasonalChart)

    val coloniesChart = ColoniesChartBuilder.createChart((controller.environmentDimension(),
      controller.statisticColonies()))

    coloniesChart.setPreferredSize(new Dimension(200, 200))
    tabbedPane.addTab("Colonies", null, coloniesChart)

    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT)

    timeLabel.setText("Execution Time: " + Time.time)
    gameBar.add(timeLabel)

    frame.add(gameBar, BorderLayout.PAGE_START)
    frame.setDefaultCloseOperation(3)
    frame.add(tabbedPane, BorderLayout.CENTER)
    frame.pack()
    frame.setVisible(true)
  }

  val tabsComponents: Map[String, Int => Component] = Map(
    "Temperature" -> (_ => heatmapChart(controller properties "Temperature")),
    "Humidity" -> (_ => heatmapChart(controller properties "Humidity")),
    "Pressure" -> (_ => heatmapChart(controller properties "Pressure")),
    "Seasonal Variation" -> (_ => SeasonalChartBuilder.createChart(controller.statisticEnvironment.variationSequence()
      .map(e => (e._1, Array((1 to e._2.size).map(_.toDouble).toArray, e._2.toArray))))),
    "Colonies" -> (idx => ColoniesChartBuilder.updateChart(tabbedPane.getComponentAt(idx)
      .asInstanceOf[ColoniesChartBuilder.ColoniesChart],
      (controller.environmentDimension(), controller.statisticColonies())))
  )

  def update(): Unit = SwingUtilities.invokeLater(() => {
    val index = tabbedPane.getSelectedIndex
    if (index >= 0) tabbedPane.setComponentAt(index, tabsComponents(tabbedPane getTitleAt index)(index))

    timeLabel.setText("Execution Time: " + Time.time)
    gameBar add timeLabel
  })

  private def heatmapChart(matrix: Matrix): Component = HeatmapChartBuilder createChart matrix
}