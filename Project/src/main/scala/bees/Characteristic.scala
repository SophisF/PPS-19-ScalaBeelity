package bees

object Characteristic {
  trait Characteristic{
    type Expression
    val name: String
    val expression: Expression
  }

  trait RangeExpression {
    characteristic: Characteristic => type Expression = (Int, Int)
  }

  //range between 18 and 36
  case class TemperatureCompatibilityCharacteristic(override val name: String, frequency: Int) extends Characteristic with RangeExpression {
    override val expression: (Int, Int) = (18, 36)
  }

}
