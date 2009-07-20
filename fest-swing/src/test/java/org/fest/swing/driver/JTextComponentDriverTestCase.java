/*
 * Created on Jul 17, 2009
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
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.driver.JTextComponentSetEditableTask.setTextFieldEditable;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.task.ComponentSetVisibleTask;
import org.testng.annotations.*;

/**
 * Base class for test cases for <code>{@link JTextComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTextComponentDriverTestCase {

  private Robot robot;
  private JTextComponentDriver driver;
  private MyWindow window;

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public final void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    driver = new JTextComponentDriver(robot);
    window = MyWindow.createNew(getClass());
    beforeShowingWindow();
    robot.showWindow(window);
  }

  void beforeShowingWindow() {}

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  @RunsInEDT
  final void requireSelectedTextInTextField(String expected) {
    assertThat(selectedTextOf(textField())).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireTextInTextField(String expected) {
    assertThat(textOf(textField())).isEqualTo(expected);
  }

  @RunsInEDT
  final void requireEmptyTextField() {
    assertThat(textOf(textField())).isNullOrEmpty();
  }

  @RunsInEDT
  final void disableTextField() {
    disable(textField());
    robot.waitForIdle();
  }

  @RunsInEDT
  final void hideWindow() {
    ComponentSetVisibleTask.hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void clearTextField() {
    setTextFieldText("");
  }

  @RunsInEDT
  final void setTextFieldText(String text) {
    setText(textField(), text);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void updateText(JTextField textField, String text) {
    setText(textField, text);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setText(final JTextField textField, final String text) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        textField.setText(text);
      }
    });
  }

  @RunsInEDT
  final void makeTextFieldEditable() {
    setTextFieldEditable(textField(), true);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void makeTextFieldNotEditable() {
    setTextFieldEditable(textField(), false);
    robot.waitForIdle();
  }

  final Robot robot() { return robot; }
  final JTextComponentDriver driver() { return driver; }
  final MyWindow window() { return window; }
  final JTextField textField() { return window.textField; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textField);
    }
  }

}