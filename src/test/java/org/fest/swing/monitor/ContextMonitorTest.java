/*
 * Created on Oct 14, 2007
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
package org.fest.swing.monitor;

import java.applet.Applet;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.*;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.AWTEvent.*;
import static java.awt.event.WindowEvent.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link ContextMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class ContextMonitorTest {

  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private ContextMonitor monitor;

  private Windows windows;
  private Context context;
  private TestWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() throws Exception {
    window = TestWindow.createNewWindow(getClass());
    windows = createMock(Windows.class);
    context = createMock(Context.class);
    monitor = new ContextMonitor(context, windows);
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    ToolkitStub toolkit = ToolkitStub.createNew();
    monitor.attachTo(toolkit);
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isSameAs(monitor);
  }

  @Test public void shouldNotProcessEventIfComponentIsNotWindowOrApplet() {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        monitor.eventDispatched(new ComponentEvent(textField().createNew(), 8));
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowOpen() {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.addContextFor(window);
        windows.attachNewWindowVisibilityMonitor(window);
        windows.markAsShowing(window);
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchWindowOpenedEventToMonitor(window);
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowOpenedAndMarkWindowAsReadyIfWindowIsFileDialog() {
    final Window w = new FileDialog(window);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.addContextFor(w);
        windows.attachNewWindowVisibilityMonitor(w);
        windows.markAsShowing(w);
        windows.markAsReady(w);
        expectEventQueueLookupFor(w);
      }

      @Override protected void codeToTest() {
        dispatchWindowOpenedEventToMonitor(w);
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowClosedAndWithRootWindow() {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.removeContextFor(window);
        windows.markAsClosed(window);
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchWindowClosedEventToMonitor(window);
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowClosedAndWithNotRootWindow() {
    final Applet applet = new Applet();
    window.add(applet);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expectEventQueueLookupFor(applet);
      }

      @Override protected void codeToTest() {
        dispatchWindowClosedEventToMonitor(applet);
      }
    }.run();
  }

  @Test public void shouldNotProcessEventWithIdWindowClosing() {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchWindowClosingEventToMonitor(window);
      }
    }.run();
  }

  @Test public void shouldAddToContextIfComponentEventQueueNotEqualToSystemEventQueue() {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.storedQueueFor(window)).andReturn(new EventQueue());
        context.addContextFor(window);
      }

      @Override protected void codeToTest() {
        dispatchWindowClosingEventToMonitor(window);
      }
    }.run();
  }

  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast")
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowNotInContext(final int eventId) {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(new ArrayList<Window>());
        context.addContextFor(window);
        windows.attachNewWindowVisibilityMonitor(window);
        windows.markAsShowing(window);
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchEventToMonitor(window, eventId);
      }
    }.run();
  }

  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast")
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndClosed(final int eventId) {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(frameInList());
        expect(windows.isClosed(window)).andReturn(true);
        context.addContextFor(window);
        windows.attachNewWindowVisibilityMonitor(window);
        windows.markAsShowing(window);
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchEventToMonitor(window, eventId);
      }
    }.run();
  }

  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast")
  public void shouldProcessEventWithIdBetweenWindowFirstAndWindowLastAndWindowInContextAndNotClosed(final int eventId) {
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(frameInList());
        expect(windows.isClosed(window)).andReturn(false);
        expectEventQueueLookupFor(window);
      }

      @Override protected void codeToTest() {
        dispatchEventToMonitor(window, eventId);
      }
    }.run();
  }

  @DataProvider(name = "eventsBetweenWindowFirstAndWindowLast")
  public Iterator<Object[]> eventsBetweenWindowFirstAndWindowLast() {
    List<Object[]> ids = new ArrayList<Object[]>();
    for (int id = WINDOW_FIRST; id <= WINDOW_LAST; id++) {
      if (id == WINDOW_OPENED || id == WINDOW_CLOSED || id == WINDOW_CLOSING) continue;
      ids.add(new Object[] { id });
    }
    return ids.iterator();
  }

  private void expectEventQueueLookupFor(Component c) {
    expect(context.storedQueueFor(c)).andReturn(c.getToolkit().getSystemEventQueue());
  }

  private void dispatchWindowOpenedEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_OPENED);
  }

  private void dispatchWindowClosedEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_CLOSED);
  }

  private void dispatchWindowClosingEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_CLOSING);
  }

  private void dispatchEventToMonitor(Component c, int eventId) {
    monitor.eventDispatched(new ComponentEvent(c, eventId));
  }

  private List<Window> frameInList() {
    List<Window> rootWindows = new ArrayList<Window>();
    rootWindows.add(window);
    return rootWindows;
  }
}
