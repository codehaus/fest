/*
 * Created on Apr 12, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.xml;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.test.EqualsHashCodeContractAssert.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link XmlAttribute}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class XmlAttributeTest implements EqualsHashCodeContractTestCase {

  private XmlAttribute attribute;

  @BeforeMethod public void setUp() {
    attribute = XmlAttribute.name("firstName").value("Anakin");
  }

  public void shouldReturnName() {
    assertThat(attribute.name()).isEqualTo("firstName");
  }

  public void shouldReturnValue() {
    assertThat(attribute.value()).isEqualTo("Anakin");
  }

  public void shouldCreateAttributeWithValueAsDouble() {
    XmlAttribute other = XmlAttribute.name("capacity").value(0.2d);
    assertThat(other.name()).isEqualTo("capacity");
    assertThat(other.value()).isEqualTo("0.2");
  }

  public void shouldCreateAttributeWithValueAsLong() {
    XmlAttribute other = XmlAttribute.name("distance").value(3l);
    assertThat(other.name()).isEqualTo("distance");
    assertThat(other.value()).isEqualTo("3");
  }

  @Test(dataProvider = "notEqualAttributes")
  public void shouldNotBeEqualToDifferentObject(XmlAttribute notEqual) {
    assertThat(attribute.equals(notEqual)).isFalse();
  }

  @DataProvider(name = "notEqualAttributes") public Object[][] notEqualAttributes() {
    return new Object[][] {
        { XmlAttribute.name("firstName").value("Leia") },
        { XmlAttribute.name("first").value("Anakin") },
        { XmlAttribute.name("lastName").value("Skywalker") }
    };
  }

  public void shouldHaveConsistentEquals() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertThat(attribute.equals(other)).isTrue();
  }

  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(attribute);
  }

  public void shouldHaveSymmetricEquals() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertEqualsIsSymmetric(attribute, other);
  }

  public void shouldHaveTransitiveEquals() {
    XmlAttribute other1 = XmlAttribute.name("firstName").value("Anakin");
    XmlAttribute other2 = XmlAttribute.name("firstName").value("Anakin");
    assertEqualsIsTransitive(attribute, other1, other2);
  }

  public void shouldMaintainEqualsAndHashCodeContract() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertMaintainsEqualsAndHashCodeContract(attribute, other);
  }

  public void shouldNotBeEqualToNull() {
    assertIsNotEqualToNull(attribute);
  }

  public void shouldNotBeEqualToObjectNotBeingOfSameClass() {
    assertThat(attribute.equals("Hello")).isFalse();
  }

  public void shouldOverrideToString() {
    assertThat(attribute.toString()).isEqualTo("XmlAttribute[name='firstName',value='Anakin']");
  }
}
