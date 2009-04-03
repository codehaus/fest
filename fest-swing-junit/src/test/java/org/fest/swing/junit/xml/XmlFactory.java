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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Understands a factory DOM-based XML elements.
 *
 * @author Alex Ruiz
 */
public class XmlFactory {

  private final Document document;

  /**
   * Creates a new </code>{@link XmlFactory}</code>.
   */
  public XmlFactory() {
    try {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    } catch (ParserConfigurationException e) {
      throw new AssertionError("Unable to create XML document");
    }
  }

  /**
   * Creates a new DOM-based XML element.
   * @param name the name of the XML element to create.
   * @return the created XML element.
   */
  public XmlElement newElement(String name) {
    Element e = document.createElement(name);
    return new XmlElement(e);
  }

  /**
   * Returns the underlying DOM document.
   * @return the underlying DOM document.
   */
  public Document target() { return document; }
}
