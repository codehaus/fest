/*
 * Created on Mar 21, 2009
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

import static java.lang.String.valueOf;
import static java.security.AccessController.doPrivileged;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;

import java.security.PrivilegedAction;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.image.ImageException;
import org.fest.swing.junit.xml.XmlNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScreenshotOnFailureResultFormatter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ScreenshotOnFailureResultFormatterTest {

  private static final String HEADLESS_MODE_PROPERTY = "java.awt.headless";

  private ScreenshotOnFailureResultFormatter formatter;

  @BeforeMethod public void setUp() {
    formatter = new ScreenshotOnFailureResultFormatter();
  }

  public void shouldWriterErrorInXmlDocumentIfScreenshotWriterCouldNotBeCreated() {
    headlessAWT(true);
    try {
      formatter.startTestSuite(new JUnitTest());
      XmlNode root = new XmlNode(formatter.rootElement());
      assertThat(root).hasSize(2);
      XmlNode errorNode = root.node(1);
      assertThat(errorNode).hasName("error")
                           .hasAttribute(name("message").value("Unable to create AWT Robot"))
                           .hasAttribute(name("type").value(ImageException.class.getName()));
    } finally {
      headlessAWT(false);
    }
  }

  private void headlessAWT(final boolean headless) {
    doPrivileged(new PrivilegedAction<Void>() {
      public Void run() {
        System.setProperty(HEADLESS_MODE_PROPERTY, valueOf(headless));
        return null;
      }
    });
  }
}
