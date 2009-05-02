/*
 * Created on May 2, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.util.ArrayList;
import java.util.List;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link FrameDisposer}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FrameDisposerTest {

  private FrameDisposer disposer;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
    disposer = new FrameDisposer();
  }

  public void shouldDisposeFrames() {
    MyWindow[] windows = MyWindow.windows();
    disposer.disposeFrames();
    for (MyWindow w : windows) assertThat(w.disposed).isTrue();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    disposer = new FrameDisposer();
  }

  @AfterMethod public void tearDown() {
    ScreenLock.instance().release(this);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow[] windows() {
      final List<MyWindow> windows = new ArrayList<MyWindow>();
      execute(new GuiTask() {
        protected void executeInEDT() {
          for (int i = 0; i < 3; i++) {
            MyWindow w = new MyWindow();
            display(w);
            windows.add(w);
          }
        }
      });
      return windows.toArray(new MyWindow[windows.size()]);
    }

    boolean disposed;

    private MyWindow() {
      super(FrameDisposerTest.class);
    }

    @Override public void dispose() {
      disposed = true;
      super.dispose();
    }
  }
}
