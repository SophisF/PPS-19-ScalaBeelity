package scala.model.time

/**
 * TODO valori variazione stagionali verranno settati dal controller (magari introducendo dei val max-min)
 */
trait PeriodicalData[E] {
  var lastInference: Int
  def inference: (PeriodicalData[E], Int) => E
}
object PeriodicalData {

  def dataAtInstant[E](data: PeriodicalData[E], time: Int = Time.time): E = data.inference(data, time)
}