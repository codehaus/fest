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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TIMESTAMP;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import java.util.Date;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link TimestampWriter}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class TimestampWriterTest {

  private Element target;
  private TimestampFormatter formatter;
  private TimestampWriter writer;

  @BeforeMethod public void setUp() {
    target = createMock(Element.class);
    formatter = createMock(TimestampFormatter.class);
    writer = new TimestampWriter(formatter);
  }

  public void shouldWriteFormattedCurrentDateAsAttribute() {
    final Date date = reportDateArgumentMatcherAndReturnItsDate();
    final String formatted = "2009-06-13T15:06:10";
    new EasyMockTemplate(formatter, target) {
      protected void expectations() {
        expect(formatter.format(date)).andReturn(formatted);
        target.setAttribute(TIMESTAMP, formatted);
        expectLastCall().once();
      }

      protected void codeToTest() {
        writer.doWrite(null, target, null);
      }
    }.run();
  }

  private Date reportDateArgumentMatcherAndReturnItsDate() {
    DateArgumentMatcher m = new DateArgumentMatcher(new Date());
    reportMatcher(m);
    return m.date();
  }

  private static class DateArgumentMatcher implements IArgumentMatcher {
    private final Date date;

    DateArgumentMatcher(Date date) {
      this.date = date;
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Date)) return false;
      Date other = (Date)argument;
      return date.equals(other) || date.before(other);
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(date);
    }

    Date date() { return date; }
  }
}
