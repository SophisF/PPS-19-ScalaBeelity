package utility

import scala.util.Random

object RandomGenerator {

  def getLowFrequency: Int = {
    getRandomNumber(1, 3)
  }

  def getMediumFrequency: Int = {
    getRandomNumber(4, 3)
  }

  def getHighFrequency: Int = {
    getRandomNumber(7, 3)
  }

  private def getRandomNumber(lowerBound: Int, interval: Int): Int = {
    lowerBound + Random.nextInt(interval)
  }

}
