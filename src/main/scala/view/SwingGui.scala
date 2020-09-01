package view

import java.awt._
import java.awt.event.KeyEvent
import java.io.File

import javax.swing._
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.{PlotOrientation, XYPlot}
import org.jfree.chart.renderer.xy.{XYItemRenderer, XYLineAndShapeRenderer}
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.xy.{DefaultXYDataset, DefaultXYZDataset, XYSeries, XYSeriesCollection, XYZDataset}
import smile.interpolation.BicubicInterpolation
import smile.plot.swing.{Contour, Palette, heatmap}

object SwingGui extends App {

  /** ************************************LINE CHART ******************************************************* */
  protected def makeTextPanel(text: String) = {
    val panel = new JPanel(false)
    val filler = new JLabel(text)
    filler.setHorizontalAlignment(0)
    panel.setLayout(new GridLayout(1, 1))
    panel.add(filler)
    panel
  }

  def createChartPanel = { // this method will create the chart panel containin the graph
    val xAxisLabel = "Month"
    val yAxisLabel = "Value"
    val dataset = createDataset
    val chart = ChartFactory.createXYLineChart("", xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, true)
    customizeChart(chart)
    new ChartPanel(chart)
  }

  private def createDataset = { // this method creates the data as time seris
    val ds = new DefaultXYDataset
    val data0: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(20, 21, 22, 23, 24, 25, 26, 25, 24, 23, 22, 21))
    ds.addSeries("Temperature", data0)
    val data1: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(30, 31, 32, 33, 34, 35, 35, 34, 33, 32, 31, 30))
    ds.addSeries("Humidity", data1)
    val data2: Array[Array[Double]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      Array(1030, 1031, 1032, 1033, 1034, 1035, 1035, 1034, 1033, 1032, 1031, 1030))
    ds.addSeries("Pressure", data2)

    ds
  }

  private def customizeChart(chart: JFreeChart): Unit = { // here we make some customization
    val plot = chart.getXYPlot
    val renderer = new XYLineAndShapeRenderer
    // sets paint color for each series
    renderer.setSeriesPaint(0, Color.RED)
    renderer.setSeriesPaint(1, Color.GREEN)
    renderer.setSeriesPaint(2, Color.YELLOW)
    // sets thickness for series (using strokes)
    renderer.setSeriesStroke(0, new BasicStroke(4.0f))
    renderer.setSeriesStroke(1, new BasicStroke(3.0f))
    renderer.setSeriesStroke(2, new BasicStroke(2.0f))
    // sets paint color for plot outlines
    plot.setOutlinePaint(Color.BLUE)
    plot.setOutlineStroke(new BasicStroke(2.0f))
    // sets renderer for lines
    plot.setRenderer(renderer)
    // sets plot background
    plot.setBackgroundPaint(Color.DARK_GRAY)
    // sets paint color for the grid lines
    plot.setRangeGridlinesVisible(true)
    plot.setRangeGridlinePaint(Color.BLACK)
    plot.setDomainGridlinesVisible(true)
    plot.setDomainGridlinePaint(Color.BLACK)
  }

  /** ************************************BUBBLE CHART ******************************************************* */

  private def createBubbleChart(xyzdataset: XYZDataset) = {
    val jfreechart = ChartFactory.createBubbleChart("Colonies", "X Axis", "Y Axis", xyzdataset, PlotOrientation.VERTICAL, true, true, false)
    val xyplot = jfreechart.getPlot.asInstanceOf[XYPlot]
    xyplot.setForegroundAlpha(0.65F)
    val xyitemrenderer = xyplot.getRenderer
    xyitemrenderer.setSeriesPaint(0, Color.blue)
    val numberaxis = xyplot.getDomainAxis.asInstanceOf[NumberAxis]
    numberaxis.setLowerMargin(0.2)
    numberaxis.setUpperMargin(0.5)
    val numberaxis1 = xyplot.getRangeAxis.asInstanceOf[NumberAxis]
    numberaxis1.setLowerMargin(0.8)
    numberaxis1.setUpperMargin(0.9)
    jfreechart
  }

  def createBubbleDataset = {
    val defaultxyzdataset = new DefaultXYZDataset
    val ad: Array[Double] = Array(30, 40, 50, 60, 70, 80)
    val ad1: Array[Double] = Array(10, 20, 30, 40, 50, 60)
    val ad2: Array[Double] = Array(4, 5, 10, 8, 9, 6)
    val ad3: Array[Array[Double]] = Array(ad, ad1, ad2)
    defaultxyzdataset.addSeries("Colony 1", ad3)
    defaultxyzdataset
  }

  def createDemoPanel = {
    val jfreechart = createBubbleChart(createBubbleDataset)
    val chartpanel = new ChartPanel(jfreechart)
    chartpanel.setDomainZoomable(true)
    chartpanel.setRangeZoomable(true)
    chartpanel
  }

  /** ************************************TABBED GUI ******************************************************* */

  //TODO: Da modificare qui
  private def createAndShowGUI(): Unit = { //Create and set up the window.


    val z = Array(
      Array(1.0, 2.0, 4.0, 1.0),
      Array(6.0, 3.0, 5.0, 2.0),
      Array(4.0, 2.0, 1.0, 5.0),
      Array(5.0, 4.0, 2.0, 3.0)
    )

    val x = Array(0.0, 1.0, 2.0, 3.0)
    val y = Array(0.0, 1.0, 2.0, 3.0)
    val bicubic = new BicubicInterpolation(x, y, z)
    val Z = Array.ofDim[Double](101, 101)
    for (i <- 0 to 100) {
      for (j <- 0 to 100)
        Z(i)(j) = bicubic.interpolate(i * 0.03, j * 0.03)
    }

    val canvas = heatmap(Z, Palette.jet(256))
    canvas.add(Contour.of(Z))

    val frame = new JFrame("ScalaBeelity")
    frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit.getScreenSize.width, Toolkit.getDefaultToolkit.getScreenSize.width))
    val tabbedPane = new JTabbedPane

    canvas.panel().setPreferredSize(new Dimension(410, 50))

    tabbedPane.addTab("Temperature", null, canvas.panel(), "Does nothing")
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1)

    val panel2 = makeTextPanel("Panel #2")
    panel2.setPreferredSize(new Dimension(410, 50))

    tabbedPane.addTab("Humidity", null, panel2, "Does twice as much nothing")
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2)

    val panel3 = makeTextPanel("Panel #3")
    panel3.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Pressure", null, panel3, "Still does nothing")
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3)

    val chart = createChartPanel
    chart.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Seasonal Variation Diagram", null, chart, "Does nothing at all")
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4)

    val bubblePanel = createDemoPanel
    bubblePanel.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Colonies", null, bubblePanel, "Does nothing at all")
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
