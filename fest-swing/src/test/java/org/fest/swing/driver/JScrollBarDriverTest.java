/*
 * Created on Feb 24, 2008
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
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JScrollBarSetValueTask.setValue;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.data.ZeroAndNegativeProvider;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JScrollBarDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JScrollBarDriverTest {

  private static final int MINIMUM = 10;
  private static final int MAXIMUM = 80;
  private static final int EXTENT = 10;

  private Robot robot;
  private JScrollBarDriver driver;
  private MyWindow window;
  private JScrollBar scrollBar;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JScrollBarDriver(robot);
    window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldPassIfValueIsEqualToExpected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    driver.requireValue(scrollBar, 30);
  }

  public void shouldFailIfValueIsNotEqualToExpected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    try {
      driver.requireValue(scrollBar, 20);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value'")
                                .contains("expected:<20> but was:<30>");
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitUpIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitUp(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll up one unit should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  public void shouldScrollUnitUpTheGivenNumberOfTimes() {
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(36);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitUpTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollUnitUp(scrollBar, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarUnitUpTheGivenNumberOfTimes() {
    hideWindow();
    try {
      driver.scrollUnitUp(scrollBar, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldScrollUnitUp() {
    driver.scrollUnitUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(31);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitUp() {
    disableScrollBar();
    try {
      driver.scrollUnitUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarUnitUp() {
    hideWindow();
    try {
      driver.scrollUnitUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitDownIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitDown(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll down one unit should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  public void shouldScrollUnitDownTheGivenNumberOfTimes() {
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(22);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitDownTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollUnitDown(scrollBar, 8);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarUnitDownTheGivenNumberOfTimes() {
    hideWindow();
    try {
      driver.scrollUnitDown(scrollBar, 8);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldScrollUnitDown() {
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(29);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitDown() {
    disableScrollBar();
    try {
      driver.scrollUnitDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarUnitDown() {
    hideWindow();
    try {
      driver.scrollUnitDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockUp(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll up one block should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  public void shouldScrollBlockUpTheGivenNumberOfTimes() {
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(50);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUpTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollBlockUp(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarBlockUpTheGivenNumberOfTimes() {
    hideWindow();
    try {
      driver.scrollBlockUp(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldScrollBlockUp() {
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(40);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUp() {
    disableScrollBar();
    try {
      driver.scrollBlockUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarBlockUp() {
    hideWindow();
    try {
      driver.scrollBlockUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockDown(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll down one block should be greater than zero, but was <", times, ">");
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  public void shouldScrollBlockUpDownTheGivenNumberOfTimes() {
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(10);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUpDown() {
    disableScrollBar();
    try {
      driver.scrollBlockDown(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarBlockUpDown() {
    hideWindow();
    try {
      driver.scrollBlockDown(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldScrollBlockDown() {
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(20);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockDown() {
    disableScrollBar();
    try {
      driver.scrollBlockDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarBlockDown() {
    hideWindow();
    try {
      driver.scrollBlockDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  public void shouldScrollToGivenPosition() {
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(68);
  }

  public void shouldScrollToMaximum() {
    driver.scrollToMaximum(scrollBar);
    assertThatScrollBarValueIsEqualTo(MAXIMUM - EXTENT); // JScrollBar value cannot go to maximum
  }

  public void shouldScrollToMinimum() {
    driver.scrollToMinimum(scrollBar);
    assertThatScrollBarValueIsEqualTo(MINIMUM);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarToGivenPosition() {
    disableScrollBar();
    try {
      driver.scrollTo(scrollBar, 68);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenScrollingNotShowingJScrollBarToGivenPosition() {
    hideWindow();
    try {
      driver.scrollTo(scrollBar, 68);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  private void assertThatScrollBarValueIsEqualTo(int expected) {
    assertThat(valueOf(scrollBar)).isEqualTo(expected);
  }

  public void shouldThrowErrorIfPositionIsLessThanMinimum() {
    try {
      driver.scrollTo(scrollBar, 0);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo("Position <0> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  public void shouldThrowErrorIfPositionIsGreaterThanMaximum() {
    try {
      driver.scrollTo(scrollBar, 90);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo("Position <90> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  @RunsInEDT
  private void disableScrollBar() {
    disable(scrollBar);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JScrollBar scrollBar = new JScrollBar();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JScrollBarDriverTest.class);
      add(scrollBar);
      scrollBar.setPreferredSize(new Dimension(20, 100));
      scrollBar.setBlockIncrement(EXTENT);
      scrollBar.setValue(30);
      scrollBar.setMinimum(MINIMUM);
      scrollBar.setMaximum(MAXIMUM);
      setPreferredSize(new Dimension(60, 200));
    }
  }
}
