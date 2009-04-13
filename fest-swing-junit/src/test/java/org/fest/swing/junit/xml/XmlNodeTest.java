/*
 * Created on Apr 13, 2009
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
import static org.fest.swing.junit.xml.XmlAttributes.attributes;

import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link XmlNode}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class XmlNodeTest {

  private XmlNode node;

  @BeforeMethod public void setUp() throws Exception {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    Element e = document.createElement("person");
    document.appendChild(e);
    node = new XmlNode(e);
  }

  public void shouldAddNewChildWithoutAttributes() {
    XmlNode child = node.addNewNode("name");
    Element childTarget = child.target();
    assertThat(childTarget.getNodeName()).isEqualTo("name");
    assertThat(childTarget.getParentNode()).isSameAs(node.target());
    assertThat(childTarget.getAttributes().getLength()).isEqualTo(0);
  }

  public void shouldAddNewChildWithAttributes() {
    XmlNode child = node.addNewNode("name", attributes(name("first").value("Leia"), name("last").value("Organa")));
    Element childTarget = child.target();
    assertThat(childTarget.getNodeName()).isEqualTo("name");
    assertThat(childTarget.getAttributes().getLength()).isEqualTo(2);
    assertThat(childTarget.getAttribute("first")).isEqualTo("Leia");
    assertThat(childTarget.getAttribute("last")).isEqualTo("Organa");
    assertThat(childTarget.getParentNode()).isSameAs(node.target());
  }
}
