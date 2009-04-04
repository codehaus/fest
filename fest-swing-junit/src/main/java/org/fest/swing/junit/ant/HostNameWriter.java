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

import java.net.UnknownHostException;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.w3c.dom.Element;

/**
 * Understands writing the name of the local host as an attribute of an XML document.
 *
 * @author Alex Ruiz
 */
class HostNameWriter extends XmlElementWriter {

  private static final String LOCALHOST = "localhost";

  private final HostNameReader reader;

  HostNameWriter() {
    this(new HostNameReader());
  }

  HostNameWriter(HostNameReader reader) {
    this.reader = reader;
  }

  void doWrite(Element target, JUnitTest suite) {
    String hostName = null;
    try {
      hostName = reader.hostName();
    } catch (UnknownHostException e) {
      hostName = LOCALHOST;
    }
    target.setAttribute(HOSTNAME, hostName);
  }
}
