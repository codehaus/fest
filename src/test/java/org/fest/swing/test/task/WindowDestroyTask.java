/*
 * Created on Sep 9, 2008
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
package org.fest.swing.test.task;

import java.awt.Window;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.timing.Condition;

import static org.fest.swing.timing.Pause.pause;

/**
 * Understands a task that hides and disposes a <code>{@link Window}</code>. This task is <b>not</b> executed in the
 * event dispatch thread.
 *
 * @author Alex Ruiz
 */
@RunsInCurrentThread
public final class WindowDestroyTask {

  /**
   * Hides and disposes the given <code>{@link Window}</code>. This action is <b>not</b> executed in the event dispatch
   * thread.
   * @param w the <code>Window</code> to hide and dispose.
   */
  public static void hideAndDispose(final Window w) {
    w.setVisible(false);
    w.dispose();
    pause(new Condition("window is closed") {
      public boolean test() {
        return !w.isShowing();
      }
    });
  }

  private WindowDestroyTask() {}
}
