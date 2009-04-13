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
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.test.EqualsHashCodeContractAssert.*;

import java.util.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link XmlAttributes}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class XmlAttributesTest implements EqualsHashCodeContractTestCase {

  private XmlAttributes attributes;

  @BeforeMethod public void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @Test(dataProvider = "notEqualAttributes")
  public void shouldNotBeEqualToDifferentObject(XmlAttributes notEqual) {
    assertThat(attributes.equals(notEqual)).isFalse();
  }

  @DataProvider(name = "notEqualAttributes") public Object[][] notEqualAttributes() {
    return new Object[][] {
        { XmlAttributes.attributes(name("firstName").value("Han"), name("lastName").value("Solo")) },
        { XmlAttributes.attributes() },
        { XmlAttributes.attributes(name("name").value("Yoda")) }
    };
  }

  public void shouldReturnAllAttributesInIterator() {
    Iterator<XmlAttribute> iterator = attributes.iterator();
    List<XmlAttribute> attributeList = new ArrayList<XmlAttribute>();
    while (iterator.hasNext()) attributeList.add(iterator.next());
    assertThat(attributeList).containsExactly(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  public void shouldHaveConsistentEquals() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertThat(attributes.equals(other)).isTrue();
  }

  public void shouldHaveReflexiveEquals() {
    assertEqualsIsReflexive(attributes);
  }

  public void shouldHaveSymmetricEquals() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsSymmetric(attributes, other);
  }

  public void shouldHaveTransitiveEquals() {
    XmlAttributes other1 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    XmlAttributes other2 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsTransitive(attributes, other1, other2);
  }

  public void shouldMaintainEqualsAndHashCodeContract() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertMaintainsEqualsAndHashCodeContract(attributes, other);
  }

  public void shouldNotBeEqualToNull() {
    assertThat(attributes.equals(null)).isFalse();
  }

  public void shouldNotBeEqualToObjectNotBeingOfSameClass() {
    assertThat(attributes.equals("Hello")).isFalse();
  }
}
