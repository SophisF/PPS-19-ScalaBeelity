package view

import java.awt._
import java.awt.event.KeyEvent
import java.io.File

import javax.swing._
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.{ChartFactory, ChartPanel, JFreeChart}
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}
import smile.interpolation.BicubicInterpolation
import smile.plot.swing.{Contour, Palette, heatmap}

object SwingGui extends App {

  protected def makeTextPanel(text: String) = {
    val panel = new JPanel(false)
    val filler = new JLabel(text)
    filler.setHorizontalAlignment(0)
    panel.setLayout(new GridLayout(1, 1))
    panel.add(filler)
    panel
  }

  def createChartPanel = { // this method will create the chart panel containin the graph
    val chartTitle = "Objects Movement Chart"
    val xAxisLabel = "X"
    val yAxisLabel = "Y"
    //val dataset = createDataset
    val chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, /*dataset*/ null, PlotOrientation.VERTICAL, true, true, true)
    customizeChart(chart)
    // saves the chart as an image files
    val imageFile = new File("XYLineChart.png")
    val width = 640
    val height = 480
    //    try ChartUtilities.saveChartAsPNG(imageFile, chart, width, height)
    //    catch {
    //      case ex: IOException =>
    //        System.err.println(ex)
    //    }
    new ChartPanel(chart)
  }

  private def createDataset = { // this method creates the data as time seris
    val dataset = new XYSeriesCollection
    val series1 = new XYSeries("Object 1")
    val series2 = new XYSeries("Object 2")
    val series3 = new XYSeries("Object 3")
    series1.add(1.0, 2.0)
    series1.add(2.0, 3.0)
    series1.add(3.0, 2.5)
    series1.add(3.5, 2.8)
    series1.add(4.2, 6.0)
    series2.add(2.0, 1.0)
    series2.add(2.5, 2.4)
    series2.add(3.2, 1.2)
    series2.add(3.9, 2.8)
    series2.add(4.6, 3.0)
    series3.add(1.2, 4.0)
    series3.add(2.5, 4.4)
    series3.add(3.8, 4.2)
    series3.add(4.3, 3.8)
    series3.add(4.5, 4.0)
    dataset.addSeries(series1)
    dataset.addSeries(series2)
    dataset.addSeries(series3)
    dataset
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

    val panel5 = makeTextPanel("Panel #5")
    panel5.setPreferredSize(new Dimension(410, 50))
    tabbedPane.addTab("Colonies", null, panel5, "Does nothing at all")
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
