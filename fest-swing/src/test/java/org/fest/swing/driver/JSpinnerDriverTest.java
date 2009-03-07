/*
 * Created on Feb 25, 2008
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

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.data.ZeroAndNegativeProvider;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSpinnerSetValueTask.setValue;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JSpinnerDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JSpinnerDriverTest {

  private Robot robot;
  private JSpinnerDriver driver;
  private MyWindow window;
  private JSpinner spinner;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSpinnerDriver(robot);
    window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIncrementValue() {
    assertFirstValueIsSelected();
    driver.increment(spinner);
    assertThatSpinnerValueIsEqualTo("Sam");
  }

  public void shouldThrowErrorWhenIncrementingValueInDisabledJSpinner() {
    disableSpinner();
    try {
      driver.increment(spinner);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenIncrementingValueInNotShowingJSpinner() {
    hideWindow();
    try {
      driver.increment(spinner);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
  
  public void shouldIncrementValueTheGivenTimes() {
    assertFirstValueIsSelected();
    driver.increment(spinner, 2);
    assertLastValueIsSelected();
  }

  public void shouldThrowErrorWhenIncrementingValueInDisabledJSpinnerTheGivenTimes() {
    disableSpinner();
    try {
      driver.increment(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenIncrementingValueInNotShowingJSpinnerTheGivenTimes() {
    hideWindow();
    try {
      driver.increment(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToIncrementIsZeroOrNegative(int times) {
    try {
      driver.increment(spinner, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to increment the value should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  public void shouldDecrementValue() {
    driver.increment(spinner);
    driver.decrement(spinner);
    assertFirstValueIsSelected();
  }

  public void shouldThrowErrorWhenDecrementingValueInDisabledJSpinner() {
    disableSpinner();
    try {
      driver.decrement(spinner);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDecrementingValueInNotShowingJSpinner() {
    hideWindow();
    try {
      driver.decrement(spinner);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  private void assertFirstValueIsSelected() {
    assertThatSpinnerValueIsEqualTo("Frodo");
  }

  public void shouldDecrementValueTheGivenTimes() {
    selectLastValue();
    driver.decrement(spinner, 2);
    assertFirstValueIsSelected();
  }

  public void shouldThrowErrorWhenDecrementingValueInDisabledJSpinnerTheGivenTimes() {
    disableSpinner();
    try {
      driver.decrement(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDecrementingValueInNotShowingJSpinnerTheGivenTimes() {
    hideWindow();
    try {
      driver.decrement(spinner, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToDecrementIsZeroOrNegative(int times) {
    try {
      driver.decrement(spinner, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to decrement the value should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test(groups = GUI, expectedExceptions=ActionFailedException.class)
  public void shouldThrowErrorIfTextComponentEditorNotFoundWhenEnteringText() {
    setJLabelAsEditorIn(spinner);
    robot.waitForIdle();
    driver.enterText(spinner, "hello");
  }

  @Test(groups = GUI, expectedExceptions=ComponentLookupException.class)
  public void shouldThrowErrorIfTextComponentEditorNotFound() {
    setJLabelAsEditorIn(spinner);
    robot.waitForIdle();
    driver.editor(spinner);
  }

  @RunsInEDT
  private static void setJLabelAsEditorIn(final JSpinner spinner) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        spinner.setEditor(new JLabel());
      }
    });
  }

  public void shouldEnterTextAndCommit() {
    driver.enterTextAndCommit(spinner, "Gandalf");
    assertLastValueIsSelected();
  }

  public void shouldThrowErrorWhenEnteringAndCommittingTextInDisabledJSpinner() {
    disableSpinner();
    try {
      driver.enterTextAndCommit(spinner, "Gandalf");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenEnteringAndCommittingTextInNotShowingJSpinner() {
    hideWindow();
    try {
      driver.enterTextAndCommit(spinner, "Gandalf");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldEnterText() {
    setValue(spinner, "Frodo");
    robot.waitForIdle();
    driver.enterText(spinner, "Gandalf");
    JTextComponent editor = driver.editor(spinner);
    assertThat(textOf(editor)).isEqualTo("Gandalf");
    assertThatSpinnerValueIsEqualTo("Frodo");
  }

  public void shouldThrowErrorWhenEnteringTextInDisabledJSpinner() {
    disableSpinner();
    try {
      driver.enterText(spinner, "Gandalf");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenEnteringTextInNotShowingJSpinner() {
    hideWindow();
    try {
      driver.enterText(spinner, "Gandalf");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldSelectValue() {
    driver.selectValue(spinner, "Gandalf");
    assertLastValueIsSelected();
  }

  public void shouldThrowErrorIfValueToSelectNotValid() {
    try {
      driver.selectValue(spinner, "Yoda");
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("Value 'Yoda' is not valid");
    }
  }

  private void assertLastValueIsSelected() {
    assertThatSpinnerValueIsEqualTo("Gandalf");
  }

  public void shouldPassIfValueIsEqualToExpected() {
    selectLastValue();
    driver.requireValue(spinner, "Gandalf");
  }

  public void shouldFailIfValueIsNotEqualToExpected() {
    selectLastValue();
    try {
      driver.requireValue(spinner, "Frodo");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value'")
                                .contains("expected:<'Frodo'> but was:<'Gandalf'>");
    }
  }

  @RunsInEDT
  private void selectLastValue() {
    setValue(spinner, "Gandalf");
    robot.waitForIdle();
  }

  @RunsInEDT
  private void assertThatSpinnerValueIsEqualTo(Object expected) {
    assertThat(valueOf(spinner)).isEqualTo(expected);
  }

  @RunsInEDT
  private void disableSpinner() {
    disable(spinner);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JSpinnerDriverTest.class);
      add(spinner);
      setPreferredSize(new Dimension(160, 80));
    }
  }
}
