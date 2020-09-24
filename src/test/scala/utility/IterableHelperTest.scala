package scala.utility

import org.scalatest.funsuite.AnyFunSuite

import scala.utility.IterableHelper.RichIterable

class IterableHelperTest extends AnyFunSuite {

  test("RichIterator should make mirroring of an iterator.") {
    val it = Iterable.range(1, 5).mirror().toList
    assert(it == List(1, 2, 3, 4, 4, 3, 2, 1))
  }

  test("RichIterator should make mirroring of an iterator without center element, if we pass false value.") {
    val it = Iterable.range(1, 5).mirror(false).toList
    assert(it == List(1, 2, 3, 4, 3, 2, 1))
  }

  test("RichIterator should make mirroring of an any type of data, such as strings.") {
    val it = Iterable("a", "number", "of", "words").mirror().toList
    assert(it == List("a", "number", "of", "words", "words", "of", "number", "a"))
  }

  test("RichIterator should make mirroring of an any type of data, such as double.") {
    val it = Iterable(0.2, 0.3, 0.4).mirror().toList
    assert(it == List(0.2, 0.3, 0.4, 0.4, 0.3, 0.2))
  }
}
