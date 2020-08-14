package bees.bee

object ColonyArea {
  trait ColonyArea{
    val center: (Int, Int)
    def getArea(): Int
    def *(x: Int): Int
    def getEnds(): Ends
  }

  case class ColonyAreaImpl(override val center: (Int, Int) , dimension: Int) extends ColonyArea{
    private var side: Int = dimension*2+1

    override def getArea(): Int = side^2

    override def *(x: Int): Int = this.getArea()*x

    override def getEnds(): Ends = new Ends {
      override val bottomLeft: (Int, Int) = (center._1-dimension, center._2-dimension)
      override val bottomRight: (Int, Int) = (center._1-dimension, center._2+dimension)
      override val topLeft: (Int, Int) = (center._1+dimension, center._2-dimension)
      override val topRight: (Int, Int) = (center._1+dimension, center._2+dimension)
    }
  }

  trait Ends{
    val bottomLeft: (Int, Int)
    val bottomRight: (Int, Int)
    val topLeft: (Int, Int)
    val topRight: (Int, Int)
  }

}
