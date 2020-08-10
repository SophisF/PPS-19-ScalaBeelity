package scala.model.time

/**
 * Represent a data that can change it's state at pre-defined time-instants
 *
 * @tparam E type of the data
 * @author Paolo Baldini
 */
trait EvolvableData[E] extends Timed {
  var evaluatedPercent: Int
  def inference: (EvolvableData[E], Int) => E
}

object EvolvableData {

  def inference[E](data: EvolvableData[E], time: Int = Time.time): Option[E] = time match {
    case time if !Timed.inProgress(data, time) => Option.empty
    case _ => Option.apply(data.inference(data, time))
  }
}
