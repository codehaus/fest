/*
 * Created on Jun 25, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.input;

import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.awt.event.WindowEvent.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.fest.swing.test.builder.JFrames.frame;

/**
 * Tests for <code>{@link DisposedWindowMonitor}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class DisposedWindowMonitorTest {

  private DisposedWindowMonitor monitor;
  private Window window;

  @BeforeMethod public void setUp() {
    monitor = new DisposedWindowMonitor();
    window = frame().createNew();
  }

  public void shouldReturnIsNotDuplicateIfEventIsNotWindowEvent() {
    assertThat(monitor.isDuplicateDispose(createMock(AWTEvent.class))).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  public void shouldReturnIsNotDuplicateIfWindowIsClosing() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSING);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  public void shouldReturnIsNotDuplicateIfWindowIsNotClosingAndIsNotClosed() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_OPENED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).isEmpty();
  }

  public void shouldReturnIsDuplicateIfWindowIsClosedAndIsInDisposedWindowsMap() {
    monitor.disposedWindows.put(window, true);
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isTrue();
    assertThat(monitor.disposedWindows).hasSize(1);
    assertThat(monitor.disposedWindows).includes(entry(window, true));
  }

  public void shouldReturnIsNotDuplicateIfWindowIsClosedAndIsNotInDisposedWindowsMap() {
    WindowEvent e = new WindowEvent(window, WINDOW_CLOSED);
    assertThat(monitor.isDuplicateDispose(e)).isFalse();
    assertThat(monitor.disposedWindows).hasSize(1)
                                       .includes(entry(window, true));
    ComponentListener[] componentListeners = window.getComponentListeners();
    assertThat(componentListeners).hasSize(1);
    assertThat(componentListeners[0]).isInstanceOf(DisposalMonitor.class);
    DisposalMonitor disposalMonitor = (DisposalMonitor)componentListeners[0];
    assertThat(disposalMonitor.disposedWindows).isSameAs(monitor.disposedWindows);
  }
}
