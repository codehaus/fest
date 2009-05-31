/*
 * Created on Nov 1, 2007
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

import java.awt.Component;
import java.awt.Container;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestMdiWindow;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.swing.TestMdiWindow.createAndShowNewWindow;

/**
 * Tests for <code>{@link ParentFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ParentFinderTest {

  private ParentFinder finder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    finder = new ParentFinder();
  }

  @AfterMethod public void tearDown() {
    ScreenLock.instance().release(this);
  }

  public void shouldReturnParentOfComponent() {
    final MyWindow window = MyWindow.createAndShow();
    try {
      Container parent = findParent(finder, window.textField);
      assertThat(parent).isSameAs(contentPaneOf(window));
    } finally {
      window.destroy();
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    final JTextField textField = new JTextField();

    private MyWindow() {
      super(ParentFinderTest.class);
      addComponents(textField);
    }
  }

  public void shouldReturnParentOfInternalFrame() {
    TestMdiWindow window = createAndShowNewWindow(getClass());
    JInternalFrame internalFrame = window.internalFrame();
    try {
      assertThat(findParent(finder, internalFrame)).isNotNull()
                                                   .isSameAs(desktopPaneOf(internalFrame));
    } finally {
      window.destroy();
    }
  }

  @RunsInEDT
  private static Container findParent(final ParentFinder finder, final Component c) {
    return execute(new GuiQuery<Container>() {
      protected Container executeInEDT() {
        return finder.parentOf(c);
      }
    });
  }

  @RunsInEDT
  private static JDesktopPane desktopPaneOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      protected JDesktopPane executeInEDT() {
        return internalFrame.getDesktopIcon().getDesktopPane();
      }
    });
  }
}
