package scala.utility

object Tuple {

  implicit class RichTuple[A, B](tuple: (A, B)) {

    def map[C](strategy: (A, B) => C): C = strategy(tuple _1, tuple _2)

    def reduce[C](strategy: (A, B) => C): C = strategy(tuple _1, tuple _2)
  }
}
