/*
 * Created on Oct 18, 2007
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

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.test.awt.ToolkitStub;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.AWTEvent.*;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <code>{@link WindowMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMonitorTest {

  private static final long WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private static final long CONTEXT_MONITOR_EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private WindowMonitor monitor;

  private ToolkitStub toolkit;
  private Windows windows;
  private Context context;
  private WindowStatus windowStatus;
  private TestWindow frame;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    toolkit = ToolkitStub.createNew();
    windows = createMock(Windows.class);
    context = createMock(Context.class);
    windowStatus = createMock(WindowStatus.class);
    frame = TestWindow.createNewWindow(getClass());
    createWindowMonitor();
  }

  private void createWindowMonitor() {
    new EasyMockTemplate(context, windows, windowStatus) {
      protected final void expectations() {
        expect(windowStatus.windows()).andReturn(windows);
        for (Frame f : Frame.getFrames()) expectToExamine(f);
      }

      private void expectToExamine(Window w) {
        expectToMarkExisting(w);
        expectToAddContextFor(w);
        windows.attachNewWindowVisibilityMonitor(w);
        for (Window owned : ownedWindowsOf(w)) expectToExamine(owned);
      }

      private void expectToMarkExisting(Window w) {
        windows.markExisting(w);
        expectLastCall().once();
      }

      private void expectToAddContextFor(Window w) {
        context.addContextFor(w);
        expectLastCall().once();
      }

      protected final void codeToTest() {
        monitor = execute(new GuiQuery<WindowMonitor>() {
          protected WindowMonitor executeInEDT() {
            return new WindowMonitor(toolkit, context, windowStatus);
          }
        });
      }
    }.run();
    reset(context);
    reset(windows);
    reset(windowStatus);
  }

  private static Window[] ownedWindowsOf(final Window w) {
    return execute(new GuiQuery<Window[]>() {
      protected Window[] executeInEDT() {
        return w.getOwnedWindows();
      }
    });
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldAttachMonitorsAndPopulateExistingWindows() {
    assertThatListenerIsUnderMask(CONTEXT_MONITOR_EVENT_MASK, ContextMonitor.class);
    assertThatListenerIsUnderMask(WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK, WindowAvailabilityMonitor.class);
  }

  private void assertThatListenerIsUnderMask(long mask, Class<? extends AWTEventListener> type) {
    AWTEventListener listener = listenerUnderMask(mask);
    assertThat(listener).isInstanceOf(type);
  }

  private AWTEventListener listenerUnderMask(long mask) {
    List<WeakEventListener> contextMonitorWrappers =
        toolkit.eventListenersUnderEventMask(mask, WeakEventListener.class);
    assertThat(contextMonitorWrappers).hasSize(1);
    return contextMonitorWrappers.get(0).underlyingListener();
  }

  @Test public void shouldReturnWindowIsReady() throws Exception {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        expect(windows.isReady(frame)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isTrue();
      }
    }.run();
  }

  @Test public void shouldCheckWindowIfWindowNotReady() throws Exception {
    new EasyMockTemplate(windows, windowStatus) {
      protected void expectations() {
        expect(windows.isReady(frame)).andReturn(false);
        windowStatus.checkIfReady(frame);
        //expect(windows.isReady(frame)).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isFalse();
      }
    }.run();
  }

  @Test public void shouldReturnEventQueueForComponent() throws Exception {
    final EventQueue queue = new EventQueue();
    new EasyMockTemplate(context) {
      protected void expectations() {
        expect(context.eventQueueFor(frame)).andReturn(queue);
      }

      protected void codeToTest() {
        assertThat(monitor.eventQueueFor(frame)).isSameAs(queue);
      }
    }.run();
  }

  @Test public void shouldReturnAllEventQueues() throws Exception {
    final List<EventQueue> allQueues = new ArrayList<EventQueue>();
    new EasyMockTemplate(context) {
      protected void expectations() {
        expect(context.allEventQueues()).andReturn(allQueues);
      }

      protected void codeToTest() {
        assertThat(monitor.allEventQueues()).isSameAs(allQueues);
      }
    }.run();
  }

  @Test public void shouldReturnRootWindows() throws Exception {
    final List<Window> rootWindows = new ArrayList<Window>();
    new EasyMockTemplate(context) {
      protected void expectations() {
        expect(context.rootWindows()).andReturn(rootWindows);
      }

      protected void codeToTest() {
        assertThat(monitor.rootWindows()).isSameAs(rootWindows);
      }
    }.run();
  }
}
