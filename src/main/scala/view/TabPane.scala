package view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Side}
import scalafx.scene.Scene
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.scene.control.{Label, Tab, TabPane}
import scalafx.scene.layout.{StackPane, VBox}
import smile.interpolation.BicubicInterpolation


object TabDemo extends JFXApp {
  val xAxis = NumberAxis("Month", 0, 12, 1)
  val yAxis = NumberAxis("Temp/Press/Hum", 0, 100, 1)

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


  stage = new PrimaryStage {
    title = "ScalaBeelity"
    scene = new Scene(1400, 1300) {
      root = new VBox {
        children = Seq(
          new TabPane {
            side = Side.Top
            rotateGraphic = true
            tabMinWidth = 50
            tabs = Seq(
              new Tab {
                text = "Temperature"
                closable = true
                content = new StackPane {
                  children = Seq(
                    //HeatMapChart<Number, Number> chart = new HeatMapChart<>();
                  )

                  padding = Insets(20)
                }
              },
              new Tab {
                text = "Humidity"
                closable = true
                content = new StackPane {
                  children = Seq(
                    new Label("Humidity")
                  )
                  padding = Insets(20)
                }
              },
              new Tab {
                text = "Pressure"
                closable = true
                content = new StackPane {
                  children = Seq(
                    new Label("Pressure")
                  )
                  padding = Insets(20)
                }
              },
              new Tab {
                text = "Seasonal Variation Diagram"
                closable = true
                content = new StackPane {
                  children = Seq(
                    new LineChart[Number, Number](xAxis, yAxis, ObservableBuffer(series1, series2))
                  )
                  padding = Insets(20)
                }
              },
              new Tab {
                text = "Colonies"
                closable = true
                content = new StackPane {
                  children = Seq(
                    new Label("Colonies")
                  )
                  padding = Insets(20)
                }
              },

            )

            tabClosingPolicy = TabPane.TabClosingPolicy.Unavailable
            selectionModel.value.selectLast()
          }
        )
      }
    }
  }

}