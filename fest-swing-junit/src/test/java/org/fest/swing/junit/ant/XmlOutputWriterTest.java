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

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.util.System.LINE_SEPARATOR;

import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.DOMElementWriter;
import org.easymock.IArgumentMatcher;
import org.easymock.internal.matchers.Equals;
import org.easymock.internal.matchers.Same;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlFactory;
import org.fest.swing.junit.xml.XmlElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link XmlOutputWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class XmlOutputWriterTest {

  private XmlOutputWriter writer;

  @BeforeMethod public void setUp() {
    writer = new XmlOutputWriter();
  }

  public void shouldWriteXmlToOutputStream() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    writer.write(xml(), out);
    String actual = new String(out.toByteArray());
    StringBuilder expected = new StringBuilder();
    expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(LINE_SEPARATOR)
            .append("<root>").append(LINE_SEPARATOR)
            .append("  <child />").append(LINE_SEPARATOR)
            .append("</root>").append(LINE_SEPARATOR);
    assertThat(actual).isEqualTo(expected.toString());
  }

  private Element xml() {
    XmlElement root = new XmlFactory().newElement("root");
    root.addElement("child");
    return root.target();
  }

  public void shouldThrowBuildExceptionIfSomethingGoesWrong() {
    final Element e = createMock(Element.class);
    final Writer anyWriter = createMock(Writer.class);
    final DOMElementWriter xmlWriter = mockXmlWriter(e);
    final IOException error = new IOException();
    new EasyMockTemplate(xmlWriter, e, anyWriter) {
      protected void expectations() throws Throwable {
        xmlWriter.write(e, anyWriter, 0, "  ");
        expectLastCall().andThrow(error);
      }

      protected void codeToTest() {
        try {
          writer.write(e, new ByteArrayOutputStream(), xmlWriter);
          fail("expecting exception");
        } catch (BuildException expected) {
          assertThat(expected.getMessage()).isEqualTo("Unable to write log file");
          assertThat(expected.getCause()).isSameAs(error);
        }
      }
    }.run();
  }

  private DOMElementWriter mockXmlWriter(final Element e) {
    final DOMElementWriter xmlWriter = createMock(DOMElementWriter.class);
    reportMatcher(new Same(e));
    reportMatcher(new AnyWriterArgumentMatcher());
    reportMatcher(new Equals(0));
    reportMatcher(new Equals("  "));
    return xmlWriter;
  }

  private static class AnyWriterArgumentMatcher implements IArgumentMatcher {
    public boolean matches(Object argument) {
      return argument instanceof Writer;
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append("[A java.io.Writer]");
    }
  }
}
