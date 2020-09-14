package view

import java.awt.{Canvas, Color, Graphics, Toolkit}

object ColoniesChart extends Canvas {

  val matrixDim = 400
  val initialX = (Toolkit.getDefaultToolkit.getScreenSize.width / 2) - (matrixDim / 2)
  //TODO: calcolare altezza gamebar se possibile.
  val initialY = (Toolkit.getDefaultToolkit.getScreenSize.height / 2) - (matrixDim / 2) - 100


  //TODO: Impostare dimensione oggetti a partire dal centro meno la dimenzion
  override def paint(g: Graphics): Unit = {
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
