/*
 * Created on Apr 2, 2009
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
package org.fest.swing.junit.ant;

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Understands writing the properties of a JUnit suite to an XML element.
 *
 * @author Alex Ruiz
 */
public class SuitePropertiesWriter extends XmlElementWriter {

  void doWrite(Element target, JUnitTest suite) {
    Document document = target.getOwnerDocument();
    Element propertiesElement = document.createElement(PROPERTIES);
    target.appendChild(propertiesElement);
    Properties properties = suite.getProperties();
    if (properties == null) return;
    Enumeration<?> propertyNames = properties.propertyNames();
    while (propertyNames.hasMoreElements())
      writeProperty(document, propertiesElement, properties, (String)propertyNames.nextElement());
  }

  private void writeProperty(Document document, Element target, Properties properties, String propertyName) {
    Element propertyElement = document.createElement(PROPERTY);
    propertyElement.setAttribute(ATTR_NAME, propertyName);
    propertyElement.setAttribute(ATTR_VALUE, properties.getProperty(propertyName));
    target.appendChild(propertyElement);
  }
}
