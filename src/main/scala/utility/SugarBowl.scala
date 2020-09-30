package scala.utility

object SugarBowl {

  implicit class RichMappable[T](element: T) {

    def when(condition: T => Boolean): Object {
      def ~>(mapper: T => T): T
    } = new {
      def ~>(mapper: T => T): T = condition(element) match {
        case false => element
        case true => mapper(element)
      }
    }

    def ~>[R](mapper: T => R): R = mapper(element)
  }

  implicit class RichMap[K, V](map: Map[K, V]) {

    def ?(key: K): Option[V] = map get key
  }

  implicit class RichOptional[A](optional: Option[A]) {

    def !(default: A): A = optional getOrElse default
  }
}
