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

  private Element target;
  private XmlNode node;

  @BeforeMethod public void setUp() throws Exception {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    target = document.createElement("person");
    document.appendChild(target);
    node = new XmlNode(target);
  }

  public void shouldReturnTarget() {
    assertThat(node.target()).isSameAs(target);
  }

  public void shouldAddNewChildWithoutAttributes() {
    XmlNode newNode = node.addNewNode("new");
    Element child = newNode.target();
    assertThat(nameOf(child)).isEqualTo("new");
    assertThat(attributeCountOf(child)).isEqualTo(0);
    assertThat(parentOf(child)).isSameAs(target);
  }

  public void shouldAddNewChildWithAttributes() {
    XmlNode newNode = node.addNewNode("new", attributes(name("name1").value("value1"), name("name2").value("value2")));
    Element child = newNode.target();
    assertThat(nameOf(child)).isEqualTo("new");
    assertThat(attributeCountOf(child)).isEqualTo(2);
    assertThat(child.getAttribute("name1")).isEqualTo("value1");
    assertThat(child.getAttribute("name2")).isEqualTo("value2");
    assertThat(parentOf(child)).isSameAs(target);
  }

  private static String nameOf(Node n) {
    return n.getNodeName();
  }

  private static Node parentOf(Node n) {
    return n.getParentNode();
  }

  public void shouldAddCDataNode() {
    node.addCdata("My CDATA");
    assertThat(childNodeCountOf(target)).isEqualTo(1);
    Node child = target.getFirstChild();
    assertThat(child).isInstanceOf(CDATASection.class);
    assertThat(dataOf(child)).isEqualTo("My CDATA");
  }

  public void shouldAddTextNode() {
    node.addText("Hello World");
    assertThat(childNodeCountOf(target)).isEqualTo(1);
    Node child = target.getFirstChild();
    assertThat(child).isInstanceOf(Text.class);
    assertThat(dataOf(child)).isEqualTo("Hello World");
  }

  private static String dataOf(Node d) {
    return ((CharacterData)d).getData();
  }

  public void shouldAddAttribute() {
    node.addAttribute(name("first").value("Leia"));
    assertThat(attributeCountOf(target)).isEqualTo(1);
    assertThat(target.getAttribute("first")).isEqualTo("Leia");
  }

  private static int childNodeCountOf(Node n) {
    return n.getChildNodes().getLength();
  }

  private static int attributeCountOf(Node n) {
    return n.getAttributes().getLength();
  }

  public void shouldReturnParentNode() {
    XmlNode newNode = node.addNewNode("new");
    XmlNode parent = newNode.parentNode();
    assertThat(parent).isEqualTo(node);
    assertThat(parent.target()).isSameAs(target);
  }

  public void shouldReturnChildCount() {
    node.addNewNode("one");
    node.addNewNode("two");
    assertThat(node.size()).isEqualTo(2);
  }

  public void shouldReturnChildAtPosition() {
    XmlNode newNode = node.addNewNode("one");
    assertThat(node.child(0)).isEqualTo(newNode);
  }

  public void shouldReturnNullChildAtPositionIsNotXmlElement() {
    node.addText("Hello");
    assertThat(node.child(0)).isNull();
  }

  public void shouldReturnAttributeValue() {
    node.addAttribute(name("first").value("Leia"));
    assertThat(node.valueOfAttribute("first")).isEqualTo("Leia");
  }

  public void shouldReturnNodeName() {
    assertThat(node.name()).isEqualTo("person");
  }

  public void shouldReturnText() {
    node.addText("Hello");
    assertThat(node.text()).isEqualTo("Hello");
  }

  public void shouldOverrideToString() {
    assertThat(node.toString()).isEqualTo("XmlNode[target=[person: null]]");
  }
}
