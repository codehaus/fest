/*
 * Created on Apr 1, 2009
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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_NAME;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link SuiteNameWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class SuiteNameWriterTest {

  private Element target;
  private SuiteNameWriter writer;

  @BeforeMethod public void setUp() {
    target = createMock(Element.class);
    writer = new SuiteNameWriter();
  }

  public void shouldWriteSuiteNameAsAttributeIfSuiteHasName() {
    final JUnitTest suite = new JUnitTest("Hello");
    new EasyMockTemplate(target) {
      protected void expectations() throws Exception {
        target.setAttribute(ATTR_NAME, "Hello");
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.doWrite(target, suite);
      }
    }.run();
  }

  public void shouldWriteUnknownAsAttributeIfSuiteDoesNotHaveName() {
    final JUnitTest suite = new JUnitTest(null);
    new EasyMockTemplate(target) {
      protected void expectations() throws Exception {
        target.setAttribute(ATTR_NAME, "unknown");
      }

      protected void codeToTest() {
        writer.doWrite(target, suite);
      }
    }.run();
  }
}
