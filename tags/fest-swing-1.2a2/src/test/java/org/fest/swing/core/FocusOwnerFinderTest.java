/*
 * Created on Apr 1, 2008
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentRequestFocusTask.giveFocusTo;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentHasFocusCondition.untilFocused;
import static org.fest.swing.timing.Pause.pause;

/**
 * Tests for <code>{@link FocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class FocusOwnerFinderTest {

  private MyWindow window;
  private JTextField textField;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createAndShow();
    textField = window.textBox;
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldFindFocusOwner() {
    giveFocusTo(textField);
    pause(untilFocused(textField));
    Component focusOwner = execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return FocusOwnerFinder.focusOwner();
      }
    });
    assertThat(focusOwner).isSameAs(textField);
  }

  public void shouldFindFocusOwnerInHierarchy() {
    giveFocusTo(textField);
    pause(untilFocused(textField));
    Component focusOwner = focusOwnerInHierarchy();
    assertThat(focusOwner).isSameAs(textField);
  }

  public void shouldFindFocusInOwnedWindow() {
    MyDialog dialog = MyDialog.createAndShow(window);
    giveFocusTo(dialog.button);
    pause(untilFocused(dialog.button));
    Component focusOwner = focusOwnerInHierarchy();
    assertThat(focusOwner).isSameAs(dialog.button);
    dialog.destroy();
  }

  @RunsInEDT
  private Component focusOwnerInHierarchy() {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return FocusOwnerFinder.focusOwnerInHierarchy();
      }
    });
  }

  private static class MyDialog extends TestDialog {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");

    @RunsInEDT
    static MyDialog createAndShow(final Frame owner) {
      return execute(new GuiQuery<MyDialog>() {
        protected MyDialog executeInEDT() {
          MyDialog dialog = new MyDialog(owner);
          dialog.displayInCurrentThread();
          return dialog;
        }
      });
    }

    private void displayInCurrentThread() {
      TestDialog.display(this);
    }

    private MyDialog(Frame owner) {
      super(owner);
      add(button);
    }
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return display(new MyWindow());
        }
      });
    }

    private MyWindow() {
      super(FocusOwnerFinderTest.class);
      addComponents(textBox);
    }
  }
}
