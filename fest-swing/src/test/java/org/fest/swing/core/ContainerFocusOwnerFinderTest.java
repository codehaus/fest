/*
 * Created on May 31, 2009
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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentRequestFocusTask.giveFocusTo;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentHasFocusCondition.untilFocused;
import static org.fest.swing.timing.Pause.pause;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link ContainerFocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerFocusOwnerFinderTest {

  private MyWindow window;
  private ContainerFocusOwnerFinder finder;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
    finder = new ContainerFocusOwnerFinder();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnNullIfContainerIsNotWindow() {
    Container c = createMock(Container.class);
    assertThat(focusOwnerOf(c)).isNull();
  }

  public void shouldReturnNullIfWindowIsNotShowing() {
    assertThat(focusOwnerOf(window)).isNull();
  }

  public void shouldReturnFocusOwnerInWindow() {
    window.display();
    JTextField focusOwner = window.textBox;
    giveFocusAndWaitTillFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  public void shouldReturnFocusOwnerInOwnedWindowWhenTopWindowDoesNotHaveFocusOwner() {
    window.display();
    MyDialog dialog = MyDialog.createAndShow(window);
    JButton focusOwner = dialog.button;
    giveFocusAndWaitTillFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  public void shouldReturnNullIfTopWindowOrOwnedWindowsDoNotHaveFocusOwner() {
    window.display();
    MyWindow window2 = MyWindow.createNew();
    window2.display();
    giveFocusAndWaitTillFocused(window2.textBox);
    assertThat(focusOwnerOf(window)).isNull();
  }

  private void giveFocusAndWaitTillFocused(Component focusOwner) {
    giveFocusTo(focusOwner);
    pause(untilFocused(focusOwner));
  }

  @RunsInEDT
  private Component focusOwnerOf(final Container c) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return finder.focusOwnerOf(c);
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
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFocusOwnerFinder.class);
      addComponents(textBox);
    }
  }
}
