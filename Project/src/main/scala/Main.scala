/**
 * Attempt with ScalaFX. In MacOS doesn't work.
 */
/*
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.Text

object ScalaFXHelloWorld extends JFXApp {

  stage = new PrimaryStage {
    title = "ScalaFX Hello World"
    scene = new Scene {
      fill = Black
      content = new HBox {
        padding = Insets(20)
        children = Seq(
          new Text {
            text = "Hello "
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(PaleGreen, SeaGreen))
          },
          new Text {
            text = "World!!!"
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Cyan, DodgerBlue)
            )
            effect = new DropShadow {
              color = DodgerBlue
              radius = 25
              spread = 0.25
            }
          }
        )
      }
    }
  }
}*/


/**
 * Attempt with SPlot.
 */
object Main extends App {

  import com.datawizards.splot.api.implicits._

  // Plot bar chart:
  Seq(1.0, 4.0, 9.0).plotBar()  //
  //  // Plot line chart:
  //  data.plotLine()
  //
  //  // Plot scatter chart:
  //  data.plotScatter()
  //
  //  // Start building plot:
  //  data.buildPlot()
}