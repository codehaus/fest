/*
 * Created on Oct 9, 2007
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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Window;
import java.util.Map;
import java.util.TimerTask;

import org.fest.swing.test.core.SequentialTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Base test case for <code>{@link Windows}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class Windows_TestCase extends SequentialTestCase {

  TestWindow window;
  Windows windows;
  WindowStateAssert windowState;

  private Map<Window, TimerTask> pending;
  private Map<Window, Boolean> open;
  private Map<Window, Boolean> closed;
  private Map<Window, Boolean> hidden;

  @Override protected final void onSetUp() {
    window = TestWindow.createNewWindow(getClass());
    windows = new Windows();
    windowState = new WindowStateAssert(windows, window);
    pending = windows.pending;
    open = windows.open;
    closed = windows.closed;
    hidden = windows.hidden;
  }

  @Override protected final void onTearDown() {
    window.destroy();
  }

  @Test
  public void shouldReturnTrueIfWindowIsClosed() {
    closed.put(window, true);
    assertThat(windows.isClosed(window)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfWindowIsNotClosed() {
    closed.remove(window);
    assertThat(windows.isClosed(window)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfWindowIsOpenAndNotHidden() {
    open.put(window, true);
    hidden.remove(window);
    assertThat(windows.isReady(window)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfWindowIsOpenAndHidden() {
    open.put(window, true);
    hidden.put(window, true);
    assertThat(windows.isReady(window)).isFalse();
  }

  @Test
  public void shouldReturnFalseIfWindowIsNotOpenAndHidden() {
    open.remove(window);
    hidden.put(window, true);
    assertThat(windows.isReady(window)).isFalse();
  }

  @Test
  public void shouldReturnFalseIfWindowIsNotOpenAndNotHidden() {
    open.remove(window);
    hidden.remove(window);
    assertThat(windows.isReady(window)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfWindowIsHidden() {
    hidden.put(window, true);
    assertThat(windows.isHidden(window)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfWindowIsNotHidden() {
    hidden.remove(window);
    assertThat(windows.isHidden(window)).isFalse();
  }

  @Test
  public void shouldReturnTrueIfWindowIsPending() {
    pending.put(window, null);
    assertThat(windows.isShowingButNotReady(window)).isTrue();
  }

  @Test
  public void shouldReturnFalseIfWindowIsNotPending() {
    pending.remove(window);
    assertThat(windows.isShowingButNotReady(window)).isFalse();
  }
}
