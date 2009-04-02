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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.HOSTNAME;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import java.net.UnknownHostException;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link HostNameWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class HostNameWriterTest {

  private Element target;
  private HostNameReader reader;
  private HostNameWriter writer;

  @BeforeMethod public void setUp() {
    target = createMock(Element.class);
    reader = createMock(HostNameReader.class);
    writer = new HostNameWriter(reader);
  }

  public void shouldWriteHostNameAsAttribute() {
    final String hostName = "myHost";
    new EasyMockTemplate(reader, target) {
      protected void expectations() throws Exception {
        expect(reader.hostName()).andReturn(hostName);
        target.setAttribute(HOSTNAME, hostName);
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.doWrite(null, target, null);
      }
    }.run();
  }

  public void shouldWriteLocalHostAsAttributeIfHostNameNotObtained() {
    final UnknownHostException e = new UnknownHostException();
    new EasyMockTemplate(reader, target) {
      protected void expectations() throws Exception {
        expect(reader.hostName()).andThrow(e);
        target.setAttribute(HOSTNAME, "localhost");
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.doWrite(null, target, null);
      }
    }.run();
  }
}
