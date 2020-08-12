package scala.model.time

import scala.model.time.Timed.inProgress

/**
 * TODO valori variazione stagionali verranno settati dal controller (magari introducendo dei val max-min)
 */
trait PeriodicalData[E] {
  var lastInference: Int
  def inference: (PeriodicalData[E], Int) => E
}
object PeriodicalData {

  def dataAtInstant[E](data: PeriodicalData[E]): E = data.inference(data, Time.time)
}




/*
trait TimeData[E]
object TimeData {

  def dataAtInstant[E](data: TimeData[E])(implicit operation: TimeData[E] => E): E = operation(data)
}

trait PerishableData[E] extends TimeData[E] with Timed {
  def evaluated: Int
}
object PerishableData { // TODO i.e., dato perituro: che può 'morire'

  def dataAtInstant[E](data: PerishableData[E])(implicit operation: PerishableData[E] => E): Option[E] =
    Option.when(inProgress(data, Time.time))(operation(data))
}*/