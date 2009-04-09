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

import java.security.PrivilegedAction;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.mocks.EasyMockTemplate;
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

  // TODO figure out why this test passes only when run individually
  @Test(enabled = false)
  public void shouldWriterErrorInXmlDocumentIfScreenshotWriterCouldNotBeCreated() {
    headlessAWT(true); // force an ImageException to be thrown
    try {
      formatter.startTestSuite(new JUnitTest());
      XmlNode root = formatter.xmlRootNode();
      assertThat(root.size()).isEqualTo(2);
      XmlNode errorNode = root.child(1);
      assertThat(errorNode.name()).isEqualTo("error");
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
    final XmlNode errorElement = mockXmlNode();
    new EasyMockTemplate(writer) {
      protected void expectations() {
        writer.writeScreenshot(errorElement, test);
        expectLastCall().once();
      }

      protected void codeToTest() {
        formatter.onFailureOrError(test, new Throwable(), errorElement);
      }
    }.run();
  }

  public void shouldNotTakeScreenshotWhenTestFailsIfScreenshotWriterIsNull() {
    updateWriterInFormatter(null);
    formatter.onFailureOrError(failingTest(), new Throwable(), mockXmlNode());
    // no assertions to be made...are we sure this test is meaningful?
  }

  private void updateWriterInFormatter(final ScreenshotXmlWriter writer) {
    field("screenshotXmlWriter").ofType(ScreenshotXmlWriter.class).in(formatter).set(writer);
  }

  private XmlNode mockXmlNode() {
    return createMock(XmlNode.class);
  }

  private junit.framework.Test failingTest() {
    return createMock(junit.framework.Test.class);
  }
}
