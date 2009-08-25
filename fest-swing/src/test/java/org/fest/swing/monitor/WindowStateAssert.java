/*
 * Created on Aug 25, 2009
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
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Window;
import java.util.Map;
import java.util.TimerTask;

import org.fest.assertions.AssertExtension;

/**
 * Understands assertions about the internal state of <code>{@link Windows}</code> with respect a particular
 * <code>{@link Window}</code>.
 *
 * @author Alex Ruiz
 */
class WindowStateAssert implements AssertExtension {

  private final Map<Window, TimerTask> pending;
  private final Map<Window, Boolean> open;
  private final Map<Window, Boolean> hidden;
  private final Map<Window, Boolean> closed;

  private final Window window;

  WindowStateAssert(Windows windows, Window window) {
    pending = windows.pending;
    open = windows.open;
    hidden = windows.hidden;
    closed = windows.closed;
    this.window = window;
  }

  WindowStateAssert isClosed() {
    assertThat(isWindowClosed()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotClosed() {
    assertThat(isWindowClosed()).isEqualTo(false);
    return this;
  }

  private boolean isWindowClosed() {
    return closed.containsKey(window);
  }

  WindowStateAssert isPending() {
    assertThat(isWindowPending()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotPending() {
    assertThat(isWindowPending()).isEqualTo(false);
    return this;
  }

  private boolean isWindowPending() {
    return pending.containsKey(window);
  }

  WindowStateAssert isOpen() {
    assertThat(isWindowOpen()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotOpen() {
    assertThat(isWindowOpen()).isEqualTo(false);
    return this;
  }

  private boolean isWindowOpen() {
    return open.containsKey(window);
  }

  WindowStateAssert isHidden() {
    assertThat(isWindowHidden()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotHidden() {
    assertThat(isWindowHidden()).isEqualTo(false);
    return this;
  }

  private boolean isWindowHidden() {
    return hidden.containsKey(window);
  }

  WindowStateAssert isReady() {
    isNotClosed();
    isNotHidden();
    isOpen();
    isNotPending();
    return this;
  }
}
