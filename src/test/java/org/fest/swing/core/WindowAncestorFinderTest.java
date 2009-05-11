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

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentParentQuery.parentOf;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link WindowAncestorFinder}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class WindowAncestorFinderTest {

  private MyWindow frame;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    frame = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  public void shouldFindWindowAncestor() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.button);
    assertThat(ancestor).isSameAs(frame);
  }

  public void shouldReturnNullIfComponentIsNull() {
    assertThat(WindowAncestorFinder.windowAncestorOf(null)).isSameAs(null);
  }

  public void shouldReturnWindowAsItsOwnAncestor() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame);
    assertThat(ancestor).isSameAs(frame);
  }

  public void shouldReturnInvokerAncestorAsAncestorIfComponentIsMenuElement() {
    Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
    robot.showWindow(frame);
    robot.showPopupMenu(frame.textField);
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
    assertThat(ancestor).isSameAs(frame);
    robot.cleanUp();
  }

  public void shouldReturnParentAsAncestorIfComponentIsMenuElementAndInvokerIsNull() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
    assertThat(ancestor).isSameAs(parentOf(frame.popupMenu));
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
 
    final JButton button = new JButton("Click Me");
    final JTextField textField = new JTextField(20);
    final JPopupMenu popupMenu = new JPopupMenu();

    private MyWindow() {
      super(WindowAncestorFinderTest.class);
      add(button);
      add(textField);
      textField.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("Frodo"));
    }
  }

}
