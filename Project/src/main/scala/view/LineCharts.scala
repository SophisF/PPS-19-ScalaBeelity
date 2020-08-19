/*
 * Copyright 2013 ScalaFX Project
 * All right reserved.
 */
package view

import scalafx.application.JFXApp
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}

/** A chart in which lines connect a series of data points. Useful for viewing
 * data trends over time.
 *
 * @see scalafx.scene.chart.LineChart
 * @see scalafx.scene.chart.Chart
 * @see scalafx.scene.chart.Axis
 * @see scalafx.scene.chart.NumberAxis
 * @related charts/AreaChart
 * @related charts/ScatterChart
 */
object LineChartSample extends JFXApp {



  stage = new JFXApp.PrimaryStage {
    title = "Line Chart Example"
    scene = new Scene {
      root = {

        val xAxis = NumberAxis("Values for X-Axis", 0, 3, 1)
        val yAxis = NumberAxis("Values for Y-Axis", 0, 3, 1)

        // Helper function to convert a tuple to `XYChart.Data`
        val toChartData = (xy: (Double, Double)) => XYChart.Data[Number, Number](xy._1, xy._2)

        val series1 = new XYChart.Series[Number, Number] {
          name = "Series 1"
          data = Seq(
            (0.0, 1.0),
            (1.2, 1.4),
            (2.2, 1.9),
            (2.7, 2.3),
            (2.9, 0.5)).map(toChartData)
        }

        val series2 = new XYChart.Series[Number, Number] {
          name = "Series 2"
          data = Seq(
            (0.0, 1.6),
            (0.8, 0.4),
            (1.4, 2.9),
            (2.1, 1.3),
            (2.6, 0.9)).map(toChartData)
        }

        new LineChart[Number, Number](xAxis, yAxis, ObservableBuffer(series1, series2))
      }
    }
  }
}