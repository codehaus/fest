/*
 * Created on Oct 26, 2007
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JTextField;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.builder.JMenus;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestMdiWindow;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.hierarchy.ContainerComponentsQuery.componentsOf;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.swing.TestMdiWindow.createAndShowNewWindow;

/**
 * Tests for <code>{@link ChildrenFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class ChildrenFinderTest {

  private ChildrenFinder finder;

  private JDesktopPaneChildrenFinder desktopPaneChildrenFinder;
  private JMenuChildrenFinder menuChildrenFinder;
  private WindowChildrenFinder windowChildrenFinder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    finder = new ChildrenFinder();
    desktopPaneChildrenFinder = new JDesktopPaneChildrenFinder();
    menuChildrenFinder = new JMenuChildrenFinder();
    windowChildrenFinder = new WindowChildrenFinder();
  }

  @Test(groups = GUI)
  public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() {
    ScreenLock.instance().acquire(this);
    TestMdiWindow window = createAndShowNewWindow(getClass());
    iconify(window.internalFrame());
    JDesktopPane desktop = window.desktop();
    try {
      assertThat(children(finder, desktop)).containsOnly(childrenOf(desktop));
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }

  public void shouldReturnPopupMenuIfComponentIsJMenu() {
    JMenu menu = JMenus.menu().createNew();
    assertThat(children(finder, menu)).containsOnly(childrenOf(menu));
  }

  @Test(groups = GUI)
  public void shouldReturnOwnedWindowsIfComponentIsWindow() {
    ScreenLock.instance().acquire(this);
    TestWindow window = TestWindow.createAndShowNewWindow(getClass());
    TestDialog dialog = TestDialog.createAndShowNewDialog(window);
    try {
      assertThat(children(finder, window)).containsOnly(childrenOf(window));
    } finally {
      try {
        dialog.destroy();
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }

  @Test(groups = GUI)
  public void shouldReturnChildrenOfContainer() {
    ScreenLock.instance().acquire(this);
    final MyWindow window = MyWindow.createAndShow();
    Collection<Component> children = execute(new GuiQuery<Collection<Component>>() {
      protected Collection<Component> executeInEDT() {
        return finder.childrenOf(window.getContentPane());
      }
    });
    try {
      assertThat(children).containsOnly(window.textField);
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }

  @RunsInEDT
  private static Collection<Component> children(final ChildrenFinder finder, final Component c) {
    return execute(new GuiQuery<Collection<Component>>() {
      protected Collection<Component> executeInEDT() {
        return finder.childrenOf(c);
      }
    });
  }

  @RunsInEDT
  private Object[] childrenOf(final Container c) {
    return execute(new GuiQuery<Object[]>() {
      protected Object[] executeInEDT() {
        List<Component> children = new ArrayList<Component>();
        children.addAll(componentsOf(c));
        children.addAll(desktopPaneChildrenFinder.nonExplicitChildrenOf(c));
        children.addAll(menuChildrenFinder.nonExplicitChildrenOf(c));
        children.addAll(windowChildrenFinder.nonExplicitChildrenOf(c));
        return children.toArray();
      }
    });
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

    final JTextField textField = new JTextField(20);

    private MyWindow() {
      super(ChildrenFinderTest.class);
      addComponents(textField);
    }
  }
}
