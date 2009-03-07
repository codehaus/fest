/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestMdiWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JInternalFrameDesktopPaneQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JInternalFrameDesktopPaneQueryTest {

  private TestMdiWindow window;
  private JInternalFrame internalFrame;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = TestMdiWindow.createAndShowNewWindow(getClass());
    internalFrame = window.internalFrame();
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnNullIfJDesktopIconInJInternalFrameIsNull() {
    JDesktopPane desktopPane = setNullIconAndReturnDesktopPane(internalFrame);
    assertThat(desktopPane).isNull();
  }

  @RunsInEDT
  private static JDesktopPane setNullIconAndReturnDesktopPane(final JInternalFrame internalFrame) {
    JDesktopPane desktopPane = execute(new GuiQuery<JDesktopPane>() {
      protected JDesktopPane executeInEDT() {
        internalFrame.setDesktopIcon(null);
        return JInternalFrameDesktopPaneQuery.desktopPaneOf(internalFrame);
      }
    });
    return desktopPane;
  }

  public void shouldReturnJDesktopPaneFromJDesktopIconInJInternalFrameIsNull() {
    JDesktopPane desktopPane = desktopFrameOf(internalFrame);
    assertThat(desktopPane).isSameAs(window.desktop());
  }

  @RunsInEDT
  private static JDesktopPane desktopFrameOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      protected JDesktopPane executeInEDT() {
        return JInternalFrameDesktopPaneQuery.desktopPaneOf(internalFrame);
      }
    });
  }
}
