package scala.utility

object SugarBowl {

  implicit class RichMappable[T](element: T) {

    def ~>[R](mapper: T => R): R = mapper(element)
  }

  implicit class RichMap[K, V](map: Map[K, V]) {

    def ?(key: K): Option[V] = map get key
  }

  implicit class RichOptional[A](optional: Option[A]) {

    def !(default: A): A = optional getOrElse default
  }
}
