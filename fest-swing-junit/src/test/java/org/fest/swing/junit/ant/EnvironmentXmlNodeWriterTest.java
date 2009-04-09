/*
 * Created on Apr 6, 2009
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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.HOSTNAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TIMESTAMP;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;

import java.net.UnknownHostException;
import java.util.Date;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlAttribute;
import org.fest.swing.junit.xml.XmlNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link EnvironmentXmlNodeWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class EnvironmentXmlNodeWriterTest {

  private XmlNode targetNode;
  private TimestampFormatter timeStampFormatter;
  private HostNameReader hostNameReader;
  private EnvironmentXmlNodeWriter writer;

  @BeforeMethod public void setUp() {
    targetNode = createMock(XmlNode.class);
    timeStampFormatter = createMock(TimestampFormatter.class);
    hostNameReader = createMock(HostNameReader.class);
    writer = new EnvironmentXmlNodeWriter(timeStampFormatter, hostNameReader);
  }

  public void shouldWriteFormattedCurrentDateAsAttribute() {
    final Date date = new Date();
    reportBeforeOrEqualDateArgumentMatcher(date);
    final String formatted = "2009-06-13T15:06:10";
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      protected void expectations() {
        expect(timeStampFormatter.format(date)).andReturn(formatted);
        targetNode.addAttribute(XmlAttribute.name(TIMESTAMP).value(formatted));
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThat(writer.writeTimestamp(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  private void reportBeforeOrEqualDateArgumentMatcher(Date date) {
    reportMatcher(new BeforeOrEqualDateArgumentMatcher(date));
  }

  public void shouldWriteHostNameAsAttribute() {
    final String hostName = "myHost";
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      protected void expectations() throws Exception {
        expect(hostNameReader.hostName()).andReturn(hostName);
        targetNode.addAttribute(name(HOSTNAME).value(hostName));
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  public void shouldWriteLocalHostAsAttributeIfHostNameNotObtained() {
    final UnknownHostException e = new UnknownHostException();
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      protected void expectations() throws Exception {
        expect(hostNameReader.hostName()).andThrow(e);
        targetNode.addAttribute(name(HOSTNAME).value("localhost"));
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  private static class BeforeOrEqualDateArgumentMatcher implements IArgumentMatcher {
    private final Date date;

    BeforeOrEqualDateArgumentMatcher(Date date) {
      this.date = date;
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Date)) return false;
      Date other = (Date)argument;
      return date.before(other) || date.equals(other);
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(date);
    }

    Date date() { return date; }
  }
}
