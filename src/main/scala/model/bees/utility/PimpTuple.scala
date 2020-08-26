package scala.model.bees.utility

object PimpTuple {

  implicit class MyTuple(tuple: (Int, Int)){
    def intersection(other: (Int, Int)): Int = {
      if(tuple._1 >= other._1 && tuple._1 <= other._2){
        val max: Int = math.min(tuple._2, other._2)
        max - tuple._1 + 1
      }
        else if(other._1 >= tuple._1 && other._1 <= tuple._2){
        val max: Int = math.min(tuple._2, other._2)
        max - other._1 + 1
      }
      else 0
    }

    def applyOperation(value: Int)(op: (Int, Int) => Int): (Int, Int) = {
      (op(tuple._1, value), op(tuple._2, value))
    }
  }

}
