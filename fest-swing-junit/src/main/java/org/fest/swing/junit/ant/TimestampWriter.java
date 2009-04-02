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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TIMESTAMP;

import java.util.Date;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Understands the current date and time as an attribute of an XML element..
 *
 * @author Alex Ruiz
 */
class TimestampWriter extends XmlWriter {

  private final TimestampFormatter formatter;

  TimestampWriter() {
    this(new TimestampFormatter());
  }

  TimestampWriter(TimestampFormatter formatter) {
    this.formatter = formatter;
  }

  void doWrite(Document document, Element target, JUnitTest suite) {
    target.setAttribute(TIMESTAMP, formatter.format(new Date()));
  }
}
