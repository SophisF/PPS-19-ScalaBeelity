package scala.view

import java.awt.Component


trait Chart[T] {

  def createChart(data: T): Component
}