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
import org.fest.swing.junit.xml.XmlDocument;
import org.fest.swing.junit.xml.XmlNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScreenshotXmlWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class ScreenshotXmlWriterTest {

  private XmlNode root;
  private XmlNode errorNode;
  private ScreenshotTaker screenshotTaker;
  private GUITestRecognizer guiTestRecognizer;
  private MyTest test;
  private ScreenshotXmlWriter writer;

  @BeforeMethod public void setUp() {
    XmlDocument document = new XmlDocument();
    root = document.newRoot("root");
    errorNode = root.addNewNode("error");
    screenshotTaker = createMock(ScreenshotTaker.class);
    guiTestRecognizer = createMock(GUITestRecognizer.class);
    test = new MyTest();
    writer = new ScreenshotXmlWriter(screenshotTaker, guiTestRecognizer);
  }

  public void shouldAddScreenshotElementToParentOfErrorElementIfTestIsGUITest() {
    final BufferedImage image = new BufferedImage(10, 10, TYPE_BYTE_BINARY);
    new EasyMockTemplate(screenshotTaker, guiTestRecognizer) {
      protected void expectations() {
        expect(guiTestRecognizer.isGUITest(testClassNameFrom(test), testMethodNameFrom(test))).andReturn(true);
        expect(screenshotTaker.takeDesktopScreenshot()).andReturn(image);
      }

      protected void codeToTest() {
        writer.writeScreenshot(errorNode, test);
        assertThat(root.size()).isEqualTo(2);
        assertThat(root.child(0)).isEqualTo(errorNode);
        XmlNode secondChild = root.child(1);
        assertThat(secondChild.name()).isEqualTo("screenshot");
        assertThat(secondChild.text()).isEqualTo(encodeBase64(image));
      }
    }.run();
  }

  public void shouldNotAddScreenshotElementToParentOfErrorElementIfTestIsNotGUITest() {
    new EasyMockTemplate(screenshotTaker, guiTestRecognizer) {
      protected void expectations() {
        expect(guiTestRecognizer.isGUITest(testClassNameFrom(test), testMethodNameFrom(test))).andReturn(false);
      }

      protected void codeToTest() {
        writer.writeScreenshot(errorNode, test);
        assertThat(root.size()).isEqualTo(1);
        assertThat(root.child(0)).isEqualTo(errorNode);
      }
    }.run();
  }

  private static class MyTest implements junit.framework.Test {
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }
}
