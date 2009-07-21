/*
 * Created on Jul 19, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.setVisible;
import static org.fest.swing.timing.Pause.pause;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.util.StopWatch;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for test cases for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class ComponentDriverTestCase {

  static final int TIME_TO_WAIT_FOR_FOCUS_GAIN = 2000;

  private Robot robot;
  private ComponentDriver driver;
  private MyWindow window;

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew(getClass());
    driver = new ComponentDriver(robot);
    beforeShowingWindow();
    robot.showWindow(window);
  }
  
  void beforeShowingWindow() {}


  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  @RunsInEDT
  final void disableButton() {
    doDisable(button());
  }

  @RunsInEDT
  final void disableTextField() {
    doDisable(textField());
  }

  @RunsInEDT
  private void doDisable(Component c) {
    disable(c);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void hideWindow() {
    setVisible(window, false);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatTextFieldIsEmpty() {
    assertThat(textOf(textField())).isEmpty();
  }

  @RunsInEDT
  final void assertThatButtonHasFocus() {
    assertThatButtonHasFocus(true);
  }

  @RunsInEDT
  final void assertThatButtonDoesNotHaveFocus() {
    assertThatButtonHasFocus(false);
  }

  @RunsInEDT
  final void assertThatButtonHasFocus(boolean expected) {
    assertThat(hasFocus(button())).isEqualTo(expected);
  }

  @RunsInEDT
  final void assertThatTextInTextFieldIs(String expected) {
    assertThat(textOf(textField())).isEqualTo(expected);
  }

  final void assertThatWaited(StopWatch stopWatch, long minimumWaitedTime) {
    long ellapsedTimeInMs = stopWatch.ellapsedTime();
    assertThat(ellapsedTimeInMs).isGreaterThanOrEqualTo(minimumWaitedTime);
  }

  final Robot robot() { return robot; }
  final ComponentDriver driver() { return driver; }
  final MyWindow window() { return window; }
  final MyButton button() { return window.button; }
  final JTextField textField() { return window.textField; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    final JTextField textField = new JTextField(20);
    final MyButton button = new MyButton("Click Me");

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField, button);
    }
  }

  static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean waitToRequestFocus;

    MyButton(String text) {
      super(text);
    }

    void waitToRequestFocus() { waitToRequestFocus = true; }

    @Override public boolean requestFocusInWindow() {
      if (waitToRequestFocus) pause(TIME_TO_WAIT_FOR_FOCUS_GAIN);
      return super.requestFocusInWindow();
    }
  }
}
