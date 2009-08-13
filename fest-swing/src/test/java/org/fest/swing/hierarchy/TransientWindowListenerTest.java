/*
 * Created on Nov 12, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.swing.timing.Pause.pause;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link TransientWindowListener}</code>.
 *
 * @author Alex Ruiz
 */
public class TransientWindowListenerTest extends EDTSafeTestCase {

  private TransientWindowListener listener;
  private WindowFilter mockWindowFilter;
  private TestDialog eventSource;
  private TestWindow parent;

  @Before public void setUp() {
    mockWindowFilter = createMock(MockWindowFilter.class);
    listener = new TransientWindowListener(mockWindowFilter);
    parent = TestWindow.createNewWindow(getClass());
    eventSource = TestDialog.createNewDialog(parent);
  }

  @After public void tearDown() {
    eventSource.destroy();
    parent.destroy();
  }

  @Test public void shouldUnfilterOpenedWindowIfImplicitFiltered() {
    assertUnfilterWindowIfImplicitFiltered(openedWindowEvent());
  }

  @Test public void shouldUnfilterShownWindowIfImplicitFiltered() {
    assertUnfilterWindowIfImplicitFiltered(shownWindowEvent());
  }

  private void assertUnfilterWindowIfImplicitFiltered(final AWTEvent event) {
    new EasyMockTemplate(mockWindowFilter) {
      protected void expectations() {
        expect(mockWindowFilter.isImplicitlyIgnored(eventSource)).andReturn(true);
        mockWindowFilter.recognize(eventSource);
        expectLastCall();
      }

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();
  }

  @Test public void shouldFilterOpenedWindowIfParentIsFiltered() {
    assertFilterWindowIfParentIsFiltered(openedWindowEvent());
  }

  @Test public void shouldFilterShownWindowIfParentIsFiltered() {
    assertFilterWindowIfParentIsFiltered(shownWindowEvent());
  }

  private void assertFilterWindowIfParentIsFiltered(final AWTEvent event) {
    new EasyMockTemplate(mockWindowFilter) {
      protected void expectations() {
        expect(mockWindowFilter.isImplicitlyIgnored(eventSource)).andReturn(false);
        expect(mockWindowFilter.isIgnored(parent)).andReturn(true);
        mockWindowFilter.ignore(eventSource);
        expectLastCall();
      }

      protected void codeToTest() {
        listener.eventDispatched(event);
      }
    }.run();
  }

  private WindowEvent openedWindowEvent() {
    return new WindowEvent(eventSource, WINDOW_OPENED);
  }

  private ComponentEvent shownWindowEvent() {
    return new ComponentEvent(eventSource, COMPONENT_SHOWN);
  }

  @Test public void shouldNotDoAnythingIfClosedWindowAlreadyFiltered() {
    new EasyMockTemplate(mockWindowFilter) {
      protected void expectations() {
        expect(mockWindowFilter.isIgnored(eventSource)).andReturn(true);
      }

      protected void codeToTest() {
        listener.eventDispatched(closedWindowEvent());
      }
    }.run();
  }

  @Test public void shouldFilterClosedWindowAfterEventIsProcessed() {
    new EasyMockTemplate(mockWindowFilter) {
      protected void expectations() {
        expect(mockWindowFilter.isIgnored(eventSource)).andReturn(false);
        mockWindowFilter.implicitlyIgnore(eventSource);
        expectLastCall();
        expect(mockWindowFilter.isImplicitlyIgnored(eventSource)).andReturn(true);
        mockWindowFilter.ignore(eventSource);
        expectLastCall();
      }

      protected void codeToTest() {
        listener.eventDispatched(closedWindowEvent());
        waitTillClosedEventIsHandled();
      }
    }.run();
  }

  @Test public void shouldNotFilterClosedWindowAfterEventIsProcessedIfWindowNotImplicitFiltered() {
    new EasyMockTemplate(mockWindowFilter) {
      protected void expectations() {
        expect(mockWindowFilter.isIgnored(eventSource)).andReturn(false);
        mockWindowFilter.implicitlyIgnore(eventSource);
        expectLastCall();
        expect(mockWindowFilter.isImplicitlyIgnored(eventSource)).andReturn(false);
      }

      protected void codeToTest() {
        listener.eventDispatched(closedWindowEvent());
        waitTillClosedEventIsHandled();
      }
    }.run();
  }

  private void waitTillClosedEventIsHandled() { pause(2000); }

  private WindowEvent closedWindowEvent() {
    return new WindowEvent(eventSource, WINDOW_CLOSED);
  }
}
