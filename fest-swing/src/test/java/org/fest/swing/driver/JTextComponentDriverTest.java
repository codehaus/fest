/*
 * Created on Jan 26, 2008
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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.driver.JTextComponentSetEditableTask.setTextFieldEditable;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertActionFailureDueToNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.Regex.regex;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.task.ComponentSetVisibleTask;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTextComponentDriverTest {

  private Robot robot;
  private JTextComponentDriver driver;
  private MyWindow window;
  private JTextField textField;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTextComponentDriver(robot);
    window = MyWindow.createNew();
    textField = window.textField;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldDeleteText() {
    driver.deleteText(textField);
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  public void shouldThrowErrorWhenDeletingTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.deleteText(textField);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDeletingTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.deleteText(textField);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldDeleteTextInEmptyTextComponent() {
    setTextFieldText("");
    driver.deleteText(textField);
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  public void shouldReplaceText() {
    setTextFieldText("Hi");
    driver.replaceText(textField, "Bye");
    assertThat(textOf(textField)).isEqualTo("Bye");
  }

  public void shouldThrowErrorWhenReplacingTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.replaceText(textField, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenReplacingTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.replaceText(textField, "Hello");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfReplacementTextIsNull() {
    driver.replaceText(textField, null);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfReplacementTextIsEmpty() {
    driver.replaceText(textField, "");
  }

  public void shouldSelectAllText() {
    setTextFieldText("Hello");
    driver.selectAll(textField);
    assertThat(selectedTextOf(textField)).isEqualTo(textOf(textField));
  }

  public void shouldThrowErrorWhenSelectingAllTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.selectAll(textField);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingAllTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.selectAll(textField);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldEnterText() {
    setTextFieldText("");
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textOf(textField)).isEqualTo(textToEnter);
  }

  public void shouldThrowErrorWhenEnteringTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.enterText(textField, "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenEnteringTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.enterText(textField, "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldSetText() {
    setTextFieldText("");
    String textToEnter = "Entering text";
    driver.setText(textField, textToEnter);
    assertThat(textOf(textField)).isEqualTo(textToEnter);
  }

  public void shouldThrowErrorWhenSettingTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.setText(textField, "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSettingTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.setText(textField, "Entering text");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldSelectTextRange() {
    driver.selectText(textField, 8, 14);
    assertThat(selectedTextOf(textField)).isEqualTo("a test");
  }

  public void shouldThrowErrorWhenSelectingTextRangeInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.selectText(textField, 8, 14);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingTextRangeInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.selectText(textField, 8, 14);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorIfIndicesAreOutOfBoundsWhenSelectingText() {
    try {
      driver.selectText(textField, 20, 22);
      failWhenExpectingException();
    } catch (ActionFailedException expected) {
      assertThat(expected.getMessage()).contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }

  public void shouldSelectGivenTextOnly() {
    setTextFieldText("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isEqualTo("llo W");
  }

  public void shouldScrollToTextToSelect() {
    JTextField tf = window.scrollToViewTextField;
    setText(tf, "one two three four five six seven eight nine ten");
    robot.waitForIdle();
    driver.selectText(tf, "ten");
    assertThat(selectedTextOf(tf)).isEqualTo("ten");
  }

  public void shouldThrowErrorWhenSelectingGivenTextInDisabledJTextComponent() {
    disableTextField();
    try {
      driver.selectText(textField, "llo W");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingGivenTextInNotShowingJTextComponent() {
    hideWindow();
    try {
      driver.selectText(textField, "llo W");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldPassIfTextComponentIsEditable() {
    makeTextFieldEditable();
    driver.requireEditable(textField);
  }

  public void shouldFailIfTextComponentIsNotEditableAndExpectingEditable() {
    makeTextFieldNotEditable();
    try {
      driver.requireEditable(textField);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTextComponentIsNotEditable() {
    makeTextFieldNotEditable();
    driver.requireNotEditable(textField);
  }

  private void makeTextFieldNotEditable() {
    setTextFieldEditable(textField, false);
    robot.waitForIdle();
  }

  public void shouldFailIfTextComponentIsEditableAndExpectingNotEditable() {
    makeTextFieldEditable();
    try {
      driver.requireNotEditable(textField);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  private void makeTextFieldEditable() {
    setTextFieldEditable(textField, true);
    robot.waitForIdle();
  }

  public void shouldPassIfHasExpectedText() {
    setTextFieldText("Hi");
    driver.requireText(textField, "Hi");
  }

  public void shouldPassIfTextMatchesPatternAsString() {
    setTextFieldText("Hi");
    driver.requireText(textField, "H.*");
  }

  public void shouldFailIfDoesNotHaveExpectedText() {
    setTextFieldText("Hi");
    try {
      driver.requireText(textField, "Bye");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'")
                                .contains("actual value:<'Hi'> is not equal to or does not match pattern:<'Bye'>");
    }
  }

  public void shouldPassIfTextMatchesPattern() {
    setTextFieldText("Hi");
    driver.requireText(textField, regex("H.*"));
  }

  public void shouldFailIfTextDoesNotMatchPattern() {
    setTextFieldText("Hi");
    try {
      driver.requireText(textField, regex("Bye"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'")
                                .contains("actual value:<'Hi'> does not match pattern:<'Bye'>");
    }
  }

  public void shouldPassIfEmpty() {
    setTextFieldText("");
    driver.requireEmpty(textField);
  }

  public void shouldPassIfTextIsNull() {
    setTextFieldText(null);
    driver.requireEmpty(textField);
  }

  public void shouldFailIfNotEmpty() {
    setTextFieldText("Hi");
    try {
      driver.requireEmpty(textField);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'text'").contains("expecting empty String but was:<'Hi'>");
    }
  }

  @RunsInEDT
  private void disableTextField() {
    disable(textField);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    ComponentSetVisibleTask.hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void setTextFieldText(String text) {
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

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");
    final JTextField scrollToViewTextField = new JTextField(10);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTextComponentDriverTest.class);
      JScrollPane scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
      final JViewport viewport = scrollPane.getViewport();
      viewport.add(new JLabel("A Label"));
      viewport.add(scrollToViewTextField);
      scrollPane.setPreferredSize(new Dimension(50, 50));
      addComponents(textField, scrollPane);
    }
  }
}
