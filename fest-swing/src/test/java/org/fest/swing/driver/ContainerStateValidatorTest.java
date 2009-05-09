/*
 * Created on Nov 17, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Container;

import javax.swing.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestMdiWindow;
import org.fest.swing.test.task.ComponentSetEnabledTask;
import org.testng.annotations.*;

/**
 * Tests for <code>{@link ContainerStateValidator}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class ContainerStateValidatorTest {

  private Robot robot;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldPassIfFrameIsResizable() {
    JFrame frame = frame().createNew();
    robot.showWindow(frame);
    validateCanResize(frame);
  }

  public void shouldFailIfFrameIsNotResizable() {
    JFrame frame = frame().resizable(false).createNew();
    robot.showWindow(frame);
    try {
      validateCanResize(frame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  public void shouldFailIfFrameIsResizableButDisabled() {
    JFrame frame = frame().createNew();
    robot.showWindow(frame);
    disable(frame);
    try {
      validateCanResize(frame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldFailIfFrameIsResizableButNotShowing() {
    JFrame frame = frame().createNew();
    try {
      validateCanResize(frame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldPassIfDialogIsResizable() {
    JDialog dialog = dialog().createNew();
    robot.showWindow(dialog);
    validateCanResize(dialog);
  }

  public void shouldFailIfDialogIsNotResizable() {
    JDialog dialog = dialog().resizable(false).createNew();
    robot.showWindow(dialog);
    try {
      validateCanResize(dialog);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  public void shouldFailIfDialogIsResizableButDisabled() {
    JDialog dialog = dialog().createNew();
    robot.showWindow(dialog);
    disable(dialog);
    try {
      validateCanResize(dialog);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @RunsInEDT
  private void disable(Container c) {
    ComponentSetEnabledTask.disable(c);
    robot.waitForIdle();
  }

  public void shouldFailIfDialogIsResizableButNotShowing() {
    JDialog dialog = dialog().createNew();
    try {
      validateCanResize(dialog);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldPassIfJInternalFrameIsResizable() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(window);
    validateCanResize(window.internalFrame());
  }

  public void shouldPassIfJInternalFrameIsResizableAndDisabled() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(window);
    disable(window.internalFrame());
    validateCanResize(window.internalFrame());
  }

  public void shouldFailIfJInternalFrameIsNotResizable() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    robot.showWindow(window);
    JInternalFrame internalFrame = window.internalFrame();
    makeNotResizable(internalFrame);
    robot.waitForIdle();
    try {
      validateCanResize(internalFrame);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  @RunsInEDT
  private static void makeNotResizable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setResizable(false);
      }
    });
  }

  public void shouldFailIfJInternalFrameIsResizableButNotShowing() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    try {
      validateCanResize(window.internalFrame());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldFailIfComponentIsNotWindow() {
    try {
      validateCanResize(button().createNew());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  private static void validateCanResize(final Container c) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ContainerStateValidator.validateCanResize(c);
      }
    });
  }
}
