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
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.junit.xml.XmlAttribute.name;

import java.security.PrivilegedAction;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.image.ImageException;
import org.fest.swing.junit.xml.XmlElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

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
    headlessAWT(true); // force an ImageException to be thrown
    try {
      formatter.startTestSuite(new JUnitTest());
      XmlElement root = new XmlElement(formatter.rootElement());
      assertThat(root).hasSize(2);
      XmlElement errorNode = root.element(1);
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

  public void shouldTakeScreenshotWhenTestFails() {
    final ScreenshotXmlWriter writer = createMock(ScreenshotXmlWriter.class);
    updateWriterInFormatter(writer);
    final junit.framework.Test test = failingTest();
    final Element errorElement = errorElement();
    new EasyMockTemplate(writer) {
      protected void expectations() {
        writer.addScreenshotToXmlElement(test, errorElement);
        expectLastCall().once();
      }

      protected void codeToTest() {
        formatter.onFailureOrError(test, new Throwable(), errorElement);
      }
    }.run();
  }

  public void shouldNotTakeScreenshotWhenTestFailsIfScreenshotWriterIsNull() {
    updateWriterInFormatter(null);
    formatter.onFailureOrError(failingTest(), new Throwable(), errorElement());
    // no assertions to be made...are we sure this test is meaningful?
  }

  private void updateWriterInFormatter(final ScreenshotXmlWriter writer) {
    field("screenshotXmlWriter").ofType(ScreenshotXmlWriter.class).in(formatter).set(writer);
  }

  private Element errorElement() {
    return createMock(Element.class);
  }

  private junit.framework.Test failingTest() {
    return createMock(junit.framework.Test.class);
  }
}
