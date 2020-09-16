package view

import java.awt.{Component, Dimension}

class ColoniesChart[T <: Seq[(Dimension, Int)]] extends Chart[T] {

  override def createChart(data: T): Component = ColoniesComponent

}
