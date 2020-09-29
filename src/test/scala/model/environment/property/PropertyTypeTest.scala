package scala.model.environment.property

import org.scalatest.funsuite.AnyFunSuite

import scala.model.environment.property.PropertyType.{propertyOf, properties, propertiesType, random}
import scala.model.environment.property.realization.{HumidityProperty, PressureProperty, TemperatureProperty}

/**
 * Test property trait
 *
 * @author Paolo Baldini
 */
class PropertyTypeTest extends AnyFunSuite {

  test("I should not be able to instantiate a PropertyValue object from outside the object") {
    assertDoesNotCompile("PropertyValue(TemperatureProperty)")
  }

  test("I should not be able to extend PropertyValue from outside the object") {
    assertDoesNotCompile("case class X extends PropertyValue[TemperatureProperty]")
  }

  test("PropertyValue should provide an apply method to get the property") {
    assert(PropertyType.Temperature().isInstanceOf[Property])
  }

  test("Temperature 'value' should contains a PropertyValue of TemperatureProperty") {
    assert(PropertyType.Temperature().isInstanceOf[TemperatureProperty])
  }

  test("'isInstanceOf' should not cause problem comparing between the single properties") {
    assert(! PropertyType.Temperature().isInstanceOf[HumidityProperty])
  }

  test("Humidity should allow an access to a HumidityProperty object") {
    assert(PropertyType.Humidity().isInstanceOf[HumidityProperty])
  }

  test("Pressure should allow an access to a PressureProperty object") {
    assert(PropertyType.Pressure().isInstanceOf[PressureProperty])
  }

  test("PropertyType should provide an implicit conversion from PropertyValue to *Property") {
    assertCompiles("val property: HumidityProperty = PropertyType.Humidity")
  }

  test("PropertyType should not provide an erroneous implicit conversion from PropertyValue to *Property") {
    assertDoesNotCompile("val property: TemperatureProperty = PropertyType.Humidity")
  }

  test("'properties' should contains TemperatureProperty") {
    assert(propertiesType.map(propertyOf).toSeq contains TemperatureProperty)
  }

  test("'properties' should contains HumidityProperty") {
    assert(propertiesType.map(propertyOf).toSeq contains HumidityProperty)
  }

  test("'properties' should contains PressureProperty") {
    assert(propertiesType.map(propertyOf).toSeq contains PressureProperty)
  }

  /* TODO test("'properties' filtering for timed-properties should not contains PressureProperty") {
    assert(! properties(_.isInstanceOf[TimeDependentProperty]).map(getPropertyFrom).contains(PressureProperty))
  }*/

  test("'properties' filtering for a specific property should return only it") {
    assert(properties[PressureProperty].toSeq contains PressureProperty)
  }

  // The two following test works with random so is not guaranteed to always pass
  test("[Partial test] 'random' should never return empty optional without filter (properties are defined)"){
    assert(! Iterator.continually(random[Property]).take(10000).exists(_.isEmpty))
  }

  test("[Partial test] 'random' should return different values (multiple properties are defined)"){
    assert(Iterator.continually(random[Property].get).take(10000).distinct.size > 1)
  }
}
