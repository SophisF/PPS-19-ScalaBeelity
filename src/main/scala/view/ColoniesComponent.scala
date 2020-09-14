package view

import java.awt.{Canvas, Color, Graphics, Toolkit}

object ColoniesComponent extends Canvas {

  val matrixDim = 400
  val initialX = (Toolkit.getDefaultToolkit.getScreenSize.width / 2) - (matrixDim / 2)
  //TODO: calcolare altezza gamebar se possibile.
  val initialY = (Toolkit.getDefaultToolkit.getScreenSize.height / 2) - (matrixDim / 2) - 100


  //TODO: Impostare dimensione oggetti a partire dal centro meno la dimenzion
  override def paint(g: Graphics): Unit = {
    //g.drawString("Hello",40,40);
    //g.setClip(0, 0, 300, 300)
    setBackground(Color.WHITE)
    //setPreferredSize(new Dimension(200, 200))
    setForeground(Color.BLACK)
    g.drawRect(initialX, initialY, matrixDim, matrixDim)

    g.setColor(Color.BLUE)
    g.fillRect(initialX + 130, initialY + 30, 100, 80)
    g.fillRect(initialX + 3, initialY + 3, 40, 40)
    g.fillRect(initialX + 200, initialY + 0, 23, 23)
  }

}

//import java.awt.{Color, EventQueue, Graphics, Point}
//
//import javax.swing.{JFrame, JPanel, UIManager, UnsupportedLookAndFeelException}
//
//
//object CoreControl {
//
//  class Grid() extends JPanel {
//    var fillCells = List(25)
//    override protected def paintComponent(g: Graphics): Unit = {
//      super.paintComponent(g)
//      for(e <- fillCells) {
//        val cellX = 10 + (e.x * 10)
//        val cellY = 10 + (e.y * 10)
//        g.setColor(Color.RED)
//        g.fillRect(cellX, cellY, 10, 10)
//      }
//      g.setColor(Color.BLACK)
//      g.drawRect(10, 10, 800, 500)
//      var num = 10
//      while( num <= 800) {
//        g.drawLine(num, 10, num, 510)
//
//        num += 10
//      }
//      var i = 10
//      while(i <= 500) {
//        g.drawLine(10, i, 810, i)
//        i += 10
//      }
//    }
//
//    def fillCell(x: Int, y: Int): Unit = {
//      fillCells += new Point(x, y)
//      repaint()
//    }
//  }
//
//  def main(a: Array[String]): Unit = {
//    EventQueue.invokeLater(new Runnable() {
//      override def run(): Unit = {
//        try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
//        catch {
//          case ex@(_: ClassNotFoundException | _: InstantiationException | _: IllegalAccessException | _: UnsupportedLookAndFeelException) =>
//
//        }
//        val grid = new CoreControl.Grid
//        val window = new JFrame
//        window.setSize(840, 560)
//        window.add(grid)
//        window.setVisible(true)
//        grid.fillCell(0, 0)
//        grid.fillCell(79, 0)
//        grid.fillCell(0, 49)
//        grid.fillCell(79, 49)
//        grid.fillCell(39, 24)
//      }
//    })
//  }
//}