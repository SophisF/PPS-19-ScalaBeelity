package utility

import scala.util.Random

object RandomGenerator {

   def getRandomNumber(lowerBound: Int, interval: Int): Int = {
    lowerBound + Random.nextInt(interval)
  }

}
