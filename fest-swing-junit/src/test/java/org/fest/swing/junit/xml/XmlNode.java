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

import org.fest.assertions.AssertExtension;
import org.w3c.dom.*;

/**
 * Understands a node in a XML document.
 *
 * @author Alex Ruiz
 */
public class XmlNode implements AssertExtension {

  private final Element element;

  XmlNode(Element e) {
    element = e;
  }

  public XmlNode addNode(String name) {
    Document document = element.getOwnerDocument();
    Element e = document.createElement(name);
    element.appendChild(e);
    return new XmlNode(e);
  }

  public Element target() { return element; }

  public XmlNode hasSize(int expected) {
    assertThat(nodes().getLength()).as("children count").isEqualTo(expected);
    return this;
  }

  public XmlNode isSameAs(XmlNode expected) {
    assertThat(element).as("target element").isSameAs(expected.target());
    return this;
  }

  public XmlNode node(int index) {
    NodeList nodes = nodes();
    assertThat(index).as("node index").isLessThan(nodes.getLength());
    return new XmlNode((Element)nodes.item(index));
  }

  private NodeList nodes() {
    return element.getChildNodes();
  }

  public XmlNode hasName(String expected) {
    assertThat(element.getNodeName()).as("name").isEqualTo(expected);
    return this;
  }

  public XmlNode hasText(String expected) {
    assertThat(element.getTextContent()).as("text").isEqualTo(expected);
    return this;
  }
}
