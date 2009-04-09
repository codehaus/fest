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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.util.System.LINE_SEPARATOR;

import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.DOMElementWriter;
import org.fest.swing.junit.xml.XmlDocument;
import org.fest.swing.junit.xml.XmlNode;
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

  public void shouldWriteXmlToOutputStream() throws Exception {
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

  private XmlNode xml() throws Exception {
    XmlNode root = new XmlDocument().newRoot("root");
    root.addNewNode("child");
    return root;
  }

  public void shouldThrowBuildExceptionIfSomethingGoesWrong() {
    XmlNode xmlNode = createMock(XmlNode.class);
    MyDOMElementWriter xmlWriter = new MyDOMElementWriter();
    try {
      writer.write(xmlNode, new ByteArrayOutputStream(), xmlWriter);
      fail("expecting exception");
    } catch (BuildException expected) {
      assertThat(expected.getMessage()).isEqualTo("Unable to write log file");
      assertThat(expected.getCause()).isSameAs(xmlWriter.error);
    }
  }
  private static class MyDOMElementWriter extends DOMElementWriter {
    final IOException error = new IOException("Thrown on purpose");

    @Override
    public void write(Element element, Writer out, int indent, String indentWith) throws IOException {
      throw error;
    }
  }
}
