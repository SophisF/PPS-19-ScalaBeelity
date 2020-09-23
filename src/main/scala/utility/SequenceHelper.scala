package scala.utility

import scala.util.Random

object SequenceHelper {

  implicit class RichSequence[A](sequence: Seq[A]) {

    // Option.when(sequence isEmpty)(sequence(Random nextInt sequence.size))
    def random(): Option[A] = sequence.size match {
      case 0 => Option.empty
      case _ => Option(sequence(Random.nextInt(sequence.size)))
    }
  }
}
