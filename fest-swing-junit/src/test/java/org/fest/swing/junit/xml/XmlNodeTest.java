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
import org.w3c.dom.*;

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
    Element child = node.addNewNode("name").target();
    assertThat(nameOf(child)).isEqualTo("name");
    assertThat(parentOf(child)).isSameAs(node.target());
    assertThat(attributeCountOf(child)).isEqualTo(0);
  }

  public void shouldAddNewChildWithAttributes() {
    Element child = node.addNewNode("name", attributes(name("first").value("Leia"),
                                                       name("last").value("Organa"))).target();
    assertThat(nameOf(child)).isEqualTo("name");
    assertThat(attributeCountOf(child)).isEqualTo(2);
    assertThat(child.getAttribute("first")).isEqualTo("Leia");
    assertThat(child.getAttribute("last")).isEqualTo("Organa");
    assertThat(parentOf(child)).isSameAs(node.target());
  }

  private static String nameOf(Element e) {
    return e.getNodeName();
  }

  private static Node parentOf(Element e) {
    return e.getParentNode();
  }

  public void shouldAddCDataNode() {
    node.addCdata("My CDATA");
    Element target = node.target();
    assertThat(childCountOf(target)).isEqualTo(1);
    Node child = target.getFirstChild();
    assertThat(child).isInstanceOf(CDATASection.class);
    CDATASection cdata = (CDATASection)child;
    assertThat(cdata.getData()).isEqualTo("My CDATA");
  }

  public void shouldAddTextNode() {
    node.addText("Hello World");
    Element target = node.target();
    assertThat(childCountOf(target)).isEqualTo(1);
    Node child = target.getFirstChild();
    assertThat(child).isInstanceOf(Text.class);
    Text text = (Text)child;
    assertThat(text.getData()).isEqualTo("Hello World");
  }

  public void shouldAddAttribute() {
    node.addAttribute(name("first").value("Leia"));
    Element target = node.target();
    assertThat(attributeCountOf(target)).isEqualTo(1);
    assertThat(target.getAttribute("first")).isEqualTo("Leia");
  }

  private int childCountOf(Element e) {
    return e.getChildNodes().getLength();
  }

  private static int attributeCountOf(Element childTarget) {
    return childTarget.getAttributes().getLength();
  }
}
