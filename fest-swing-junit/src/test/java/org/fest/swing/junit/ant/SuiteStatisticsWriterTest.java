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
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link SuiteStatisticsWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class SuiteStatisticsWriterTest {

  private Element target;
  private JUnitTest suite;
  private SuiteStatisticsWriter writer;

  @BeforeMethod public void setUp() {
    target = createMock(Element.class);
    suite = createMock(JUnitTest.class);
    writer = new SuiteStatisticsWriter();
  }

  public void shouldWriteStatisticsAsAttribute() {
    new EasyMockTemplate(suite, target) {
      protected void expectations() {
        expect(suite.runCount()).andReturn(6l);
        expect(suite.failureCount()).andReturn(2l);
        expect(suite.errorCount()).andReturn(1l);
        expect(suite.getRunTime()).andReturn(8000l);
        target.setAttribute(ATTR_TESTS, "6");
        expectLastCall().once();
        target.setAttribute(ATTR_FAILURES, "2");
        expectLastCall().once();
        target.setAttribute(ATTR_ERRORS, "1");
        expectLastCall().once();
        target.setAttribute(ATTR_TIME, "8.0");
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.doWrite(target, suite);
      }
    }.run();
  }
}
