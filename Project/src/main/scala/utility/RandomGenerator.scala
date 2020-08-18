package utility

import scala.util.Random

/**
 * Object that represent a generator of random number
 */
object RandomGenerator {

  /**
   * Get random number > lower bound
   * @param lowerBound the lower bound to generate random number
   * @param interval width of the interval of random number
   * @return a random number
   */
   def getRandomNumber(lowerBound: Int, interval: Int): Int = {
    lowerBound + Random.nextInt(interval)
  }

}
