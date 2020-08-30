import java.awt.event.KeyEvent
import java.awt.{BorderLayout, Dimension, GridLayout}

import javax.swing._
import smile.interpolation._
import smile.plot.swing._

import scala.language.postfixOps

object Main extends App {

  protected def makeTextPanel(text: String) = {
    val panel = new JPanel(false)
    val filler = new JLabel(text)
    filler.setHorizontalAlignment(0)
    panel.setLayout(new GridLayout(1, 1))
    panel.add(filler)
    panel
  }

  //TODO: Da modificare qui
  private def createAndShowGUI(): Unit = { //Create and set up the window.
    val frame = new JFrame("TabbedPaneDemo")
    frame.setDefaultCloseOperation(3)
    //Add content to the window.
    frame.add(xvfgn, BorderLayout.CENTER)
    //Display the window.
    frame.pack()
    frame.setVisible(true)
  }

  protected def createImageIcon(path: String) = {
    val imgURL = classOf[Main2].getResource(path)
    if (imgURL != null) new ImageIcon(imgURL)
    else {
      System.err.println("Couldn't find file: " + path)
      null
    }
  }


  val tabbedPane = new JTabbedPane
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


  val icon = createImageIcon("images/middle.gif")
  val panel1 = makeTextPanel("Panel #12")

  tabbedPane.addTab("Tab 1", icon, canvas.panel(), "Does nothing")
  tabbedPane.setMnemonicAt(0, KeyEvent.VK_1)

  val panel2 = makeTextPanel("Panel #2")
  tabbedPane.addTab("Tab 2", icon, panel2, "Does twice as much nothing")
  tabbedPane.setMnemonicAt(1, KeyEvent.VK_2)

  val panel3 = makeTextPanel("Panel #3")
  tabbedPane.addTab("Tab 3", icon, panel3, "Still does nothing")
  tabbedPane.setMnemonicAt(2, KeyEvent.VK_3)

  val panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).")
  panel4.setPreferredSize(new Dimension(410, 50))
  tabbedPane.addTab("Tab 4", icon, panel4, "Does nothing at all")
  tabbedPane.setMnemonicAt(3, KeyEvent.VK_4)

  //Add the tabbed pane to this panel.
  val xvfgn = new JFrame("")
  xvfgn.getContentPane.add(tabbedPane)
  xvfgn.show()

  //The following line enables to use scrolling tabs.
  tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT)


  SwingUtilities.invokeLater(new Runnable() {
    override def run(): Unit = { //Turn off metal's use of bold fonts
      UIManager.put("swing.boldMetal", false)
      createAndShowGUI()
    }
  })
}
