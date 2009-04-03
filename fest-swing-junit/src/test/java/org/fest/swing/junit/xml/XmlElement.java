/*
 * Created on Mar 20, 2009
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
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import org.fest.assertions.AssertExtension;
import org.w3c.dom.*;

/**
 * Understands a DOM-based XML element.
 *
 * @author Alex Ruiz
 */
public class XmlElement implements AssertExtension {

  private final Element element;

  public XmlElement(Element e) {
    element = e;
  }

  public XmlElement addElement(String name) {
    Document document = element.getOwnerDocument();
    Element e = document.createElement(name);
    element.appendChild(e);
    return new XmlElement(e);
  }

  public Element target() { return element; }

  public XmlElement hasSize(int expected) {
    assertThat(elements().getLength()).as("children count").isEqualTo(expected);
    return this;
  }

  public XmlElement isSameAs(XmlElement expected) {
    assertThat(element).as("target element").isSameAs(expected.target());
    return this;
  }

  public XmlElement element(int index) {
    NodeList elements = elements();
    assertThat(index).as("node index").isLessThan(elements.getLength());
    return new XmlElement((Element)elements.item(index));
  }

  private NodeList elements() {
    return element.getChildNodes();
  }

  public XmlElement hasName(String expected) {
    assertThat(element.getNodeName()).as("name").isEqualTo(expected);
    return this;
  }

  public XmlElement hasText(String expected) {
    assertThat(element.getTextContent()).as("text").isEqualTo(expected);
    return this;
  }

  public XmlElement hasAttribute(XmlAttribute attribute) {
    String value = element.getAttribute(attribute.name);
    assertThat(value).as(concat("value of attribute ", quote(attribute.name))).isEqualTo(attribute.value);
    return this;
  }
}
