package scala.utility

import org.scalatest.funsuite.AnyFunSuite
import scala.model.environment.utility.IteratorHelper.RichIterator


class IteratorHelperTest extends AnyFunSuite {

  test("RichIterator should make mirroring of an iterator.") {
    val it = Iterator.range(1, 5).mirror().toList
    assert(it == List(1, 2, 3, 4, 4, 3, 2, 1))
  }

  test("RichIterator should make mirroring of an iterator without center element, if we pass false value.") {
    val it = Iterator.range(1, 5).mirror(false).toList
    assert(it == List(1, 2, 3, 4, 3, 2, 1))
  }

  test("RichIterator should make mirroring of an any type of data, such as strings.") {
    val it = Iterator("a", "number", "of", "words").mirror().toList
    assert(it == List("a", "number", "of", "words", "words", "of", "number", "a"))
  }

  test("RichIterator should make mirroring of an any type of data, such as double.") {
    val it = Iterator(0.2, 0.3, 0.4).mirror().toList
    assert(it == List(0.2, 0.3, 0.4, 0.4, 0.3, 0.2))
  }

}
