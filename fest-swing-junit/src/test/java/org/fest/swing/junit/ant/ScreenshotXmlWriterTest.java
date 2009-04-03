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
package org.fest.swing.junit.ant;

import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.ant.ImageHandler.encodeBase64;
import static org.fest.swing.junit.ant.Tests.testClassNameFrom;
import static org.fest.swing.junit.ant.Tests.testMethodNameFrom;

import java.awt.image.BufferedImage;

import junit.framework.TestResult;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.image.ScreenshotTaker;
import org.fest.swing.junit.xml.XmlFactory;
import org.fest.swing.junit.xml.XmlElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScreenshotXmlWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ScreenshotXmlWriterTest {

  private XmlElement root;
  private XmlElement errorNode;
  private ScreenshotTaker screenshotTaker;
  private GUITestRecognizer guiTestRecognizer;
  private MyTest test;
  private ScreenshotXmlWriter writer;

  @BeforeMethod public void setUp() throws Exception {
    XmlFactory xmlFactory = new XmlFactory();
    root = xmlFactory.newElement("root");
    errorNode = root.addElement("error");
    screenshotTaker = createMock(ScreenshotTaker.class);
    guiTestRecognizer = createMock(GUITestRecognizer.class);
    test = new MyTest();
    writer = new ScreenshotXmlWriter(xmlFactory.target(), screenshotTaker, guiTestRecognizer);
  }

  public void shouldAddScreenshotElementToParentOfErrorElementIfTestIsGUITest() {
    final BufferedImage image = new BufferedImage(10, 10, TYPE_BYTE_BINARY);
    new EasyMockTemplate(screenshotTaker, guiTestRecognizer) {
      protected void expectations() {
        expect(guiTestRecognizer.isGUITest(testClassNameFrom(test), testMethodNameFrom(test))).andReturn(true);
        expect(screenshotTaker.takeDesktopScreenshot()).andReturn(image);
      }

      protected void codeToTest() {
        writer.addScreenshotToXmlElement(test, errorNode.target());
        assertThat(root).hasSize(2);
        assertThat(root.element(0)).isSameAs(errorNode);
        assertThat(root.element(1)).hasName("screenshot")
                                .hasText(encodeBase64(image));
      }
    }.run();
  }

  public void shouldMNotAddScreenshotElementToParentOfErrorElementIfTestIsNotGUITest() {
    new EasyMockTemplate(screenshotTaker, guiTestRecognizer) {
      protected void expectations() {
        expect(guiTestRecognizer.isGUITest(testClassNameFrom(test), testMethodNameFrom(test))).andReturn(false);
      }

      protected void codeToTest() {
        writer.addScreenshotToXmlElement(test, errorNode.target());
        assertThat(root).hasSize(1);
        assertThat(root.element(0)).isSameAs(errorNode);
      }
    }.run();
  }

  private static class MyTest implements junit.framework.Test {
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }
}
