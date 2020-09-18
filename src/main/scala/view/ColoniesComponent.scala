package view

import java.awt.{Canvas, Color, Graphics, Toolkit}

class ColoniesComponent[T <: Seq[((Int, Int), Int)]](data: T) extends Canvas {

  private val MULTIPLIER = 2
  //  private val MAX_DIMENSION = 800
  //  private var applyMultiplier = false;
    private val environmentSize = 400 //environment.environment.map.rows //TODO: è uguale alle colonne di solito
  //  //TODO: rimuovere computazione dentro la variabile
    private val matrixDim =
  //  if (environmentSize * MULTIPLIER <= MAX_DIMENSION) {
  //    applyMultiplier = true
      environmentSize * MULTIPLIER
  //  } else {
  //    applyMultiplier = false
  //    environmentSize
  //  }
  val initialX: Int = (Toolkit.getDefaultToolkit.getScreenSize.width / 2) - (matrixDim / 2)
  //TODO: calcolare altezza gamebar se possibile.
  val initialY: Int = (Toolkit.getDefaultToolkit.getScreenSize.height / 2) - (matrixDim / 2) - 100


  //TODO: Impostare dimensione oggetti a partire dal centro meno la dimenzion
  override def paint(g: Graphics): Unit = {
    //g.setClip(0, 0, 300, 300)
    setBackground(Color.WHITE)
    //setPreferredSize(new Dimension(200, 200))
    setForeground(Color.BLACK)
    g.drawRect(initialX, initialY, matrixDim, matrixDim)

    g.setColor(Color.BLUE)
    //data.foreach(d => g.fillRect(initialX + d._1._1 * MULTIPLIER, initialY + d._1._2 * MULTIPLIER, d._2 * MULTIPLIER, d._2 * MULTIPLIER))
    g.fillRect(initialX + 130 * MULTIPLIER, initialY + 30 * MULTIPLIER, 100 * MULTIPLIER, 80 * MULTIPLIER)
    g.fillRect(initialX + 3 * MULTIPLIER, initialY + 3 * MULTIPLIER, 40 * MULTIPLIER, 40 * MULTIPLIER)
    g.fillRect(initialX + 200 * MULTIPLIER, initialY + 0 * MULTIPLIER, 23 * MULTIPLIER, 23 * MULTIPLIER)
  }
}