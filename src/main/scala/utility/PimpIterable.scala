package scala.utility

object PimpIterable {

  implicit class MyIterable(list: Iterable[Int]){
    def average: Int = list.sum / list.size
  }

}
