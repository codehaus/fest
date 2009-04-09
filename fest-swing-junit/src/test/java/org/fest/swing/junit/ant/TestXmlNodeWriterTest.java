/*
 * Created on Apr 7, 2009
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

import static java.lang.System.currentTimeMillis;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.swing.junit.xml.XmlAttributes.attributes;
import junit.framework.TestResult;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlAttributes;
import org.fest.swing.junit.xml.XmlNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TestXmlNodeWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TestXmlNodeWriterTest {

  private XmlNode targetNode;
  private TestXmlNodeWriter writer;

  @BeforeMethod public void setUp() {
    targetNode = mockXmlNode();
    writer = new TestXmlNodeWriter();
  }

  public void shouldAddTestNodeAsChild() {
    final TestStub test = new TestStub("hello");
    final XmlNode newNode = mockXmlNode();
    new EasyMockTemplate(targetNode) {
      protected void expectations() {
        XmlAttributes attributes = attributes(name(ATTR_NAME).value("hello"), name(ATTR_CLASSNAME).value(TestStub.class.getName()));
        expect(targetNode.addNewNode(TESTCASE, attributes)).andReturn(newNode);
      }

      protected void codeToTest() {
        assertThat(writer.addNewTestXmlNode(targetNode, test)).isSameAs(newNode);
      }
    }.run();
  }

  public void shouldAddTestExecutionTimeAsAttribute() {
    final long startTime = currentTimeMillis() - 3000;
    new EasyMockTemplate(targetNode) {
      protected void expectations() {
        double executionTime = (currentTimeMillis() - startTime) / 1000.0;
        targetNode.addAttribute(name(ATTR_TIME).value(executionTime));
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.writeTestExecutionTime(targetNode, startTime);
      }
    }.run();
  }

  private XmlNode mockXmlNode() {
    return createMock(XmlNode.class);
  }

  private static class TestStub implements junit.framework.Test {
    private final String name;

    TestStub(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int countTestCases() { return 0; }

    public void run(TestResult result) {}
  }
}
