package scala.model.environment.utility

import scala.util.Random

object SequenceHelper {

  implicit class RichSequence[A](sequence: Seq[A]) {

    def random(): Option[A] = sequence.size match {
      case 0 => Option.empty
      case _ => Option(sequence(Random.nextInt(sequence.size)))
    }
  }
}
