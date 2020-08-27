package scala

class Timer(val initialTime: Long = System.currentTimeMillis()) {
  def elapsedTime(): Long = System.currentTimeMillis() - initialTime
}
