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

import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.test.util.StopWatch;
import org.fest.swing.timing.Condition;

import static java.awt.Event.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.MouseClickInfo.*;
import static org.fest.swing.driver.JTextComponentTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.setVisible;
import static org.fest.swing.test.util.StopWatch.startNewStopWatch;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.timing.Timeout.timeout;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverTest {

  private static final int PAUSE_TIME = 2000;

  private Robot robot;
  private ComponentDriver driver;
  private MyWindow window;
  private MyButton button;
  private JTextField textField;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    button = window.button;
    textField = window.textField;
    driver = new ComponentDriver(robot);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  public void shouldThrowErrorWhenClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.click(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test(dataProvider = "mouseButtons", groups = GUI)
  public void shouldClickComponentWithGivenMouseButtonOnce(MouseButton mouseButton) {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, mouseButton);
    assertThat(clickRecorder).wasClickedWith(mouseButton)
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  @DataProvider(name = "mouseButtons") public Object[][] mouseButtons() {
    return new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } };
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMouseButtonIsNull() {
    driver.click(button, (MouseButton)null);
  }

  public void shouldThrowErrorWhenClickingDisabledComponentWithGivenMouseButton() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponentWithGivenMouseButton() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.click(button, RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMouseClickInfoIsNull() {
    driver.click(button, (MouseClickInfo)null);
  }

  @Test(dataProvider = "mouseClickInfos", groups = GUI)
  public void shouldClickComponentWithGivenMouseClickInfo(MouseClickInfo mouseClickInfo) {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, mouseClickInfo);
    assertThat(clickRecorder).wasClickedWith(mouseClickInfo.button())
                             .clickedAt(centerOf(button))
                             .timesClicked(mouseClickInfo.times());
  }

  @DataProvider(name = "mouseClickInfos") public Object[][] mouseClickInfos() {
    return new Object[][] { { leftButton().times(3) }, { middleButton() }};
  }

  public void shouldThrowErrorWhenClickingDisabledComponentWithGivenMouseClickInfo() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, leftButton());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponentWithGivenMouseClickInfo() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.click(button, leftButton());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldDoubleClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.doubleClick(button);
    assertThat(clickRecorder).wasDoubleClicked()
                             .clickedAt(centerOf(button));
  }

  public void shouldThrowErrorWhenDoubleClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.doubleClick(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenDoubleClickingNotShowingComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.doubleClick(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldRightClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.rightClick(button);
    assertThat(clickRecorder).wasRightClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  public void shouldThrowErrorWhenRightClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.rightClick(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenRightClickingNotShowingComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.rightClick(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldClickComponentOnGivenPoint() {
    Point center = centerOf(button);
    Point where = new Point(center.x + 1, center.y + 1);
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, where);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(where)
                             .timesClicked(1);

  }

  public void shouldThrowErrorWhenClickingDisabledComponentAtGivenPoint() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldThrowErrorWhenClickingNotShowingComponentAtGivenPoint() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    hideWindow();
    try {
      driver.click(button, new Point(10, 10));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldGiveFocusToComponentAndWaitTillComponentIsFocused() {
    assertThat(hasFocus(button)).isFalse();
    button.waitToRequestFocus();
    final CountDownLatch done = new CountDownLatch(1);
    StopWatch stopWatch = startNewStopWatch();
    new Thread() {
      @Override public void run() {
        driver.focusAndWaitForFocusGain(button);
        done.countDown();
      }
    }.start();
    try {
      done.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    stopWatch.stop();
    assertThat(hasFocus(button)).isTrue();
    assertThatWaited(stopWatch, PAUSE_TIME);
  }

  public void shouldThrowErrorWhenGivingFocusToDisabledComponenAndWaiting() {
    disableButton();
    try {
      driver.focusAndWaitForFocusGain(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenGivingFocusToNotShowingComponenAndWaiting() {
    hideWindow();
    try {
      driver.focusAndWaitForFocusGain(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldReturnSettingsFromRobot() {
    assertThat(driver.settings()).isSameAs(robot.settings());
  }

  public void shouldPassIfActualSizeIsEqualToExpectedOne() {
    Dimension expected = sizeOf(button);
    driver.requireSize(button, expected);
  }

  public void shouldFailIfActualSizeIsNotEqualToExpectedOne() {
    try {
      driver.requireSize(button, new Dimension(0, 0));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'size'")
                                .contains("expected:<(0, 0)>")
                                .contains("but was:<");
    }
  }

  public void shouldPassIfComponentIsVisibleAsExpected() {
    driver.requireVisible(button);
  }

  public void shouldFailIfComponentIsNotVisibleAndExpectedToBeVisible() {
    hideWindow();
    try {
      driver.requireVisible(window);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'visible'")
                                .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfComponentIsNotVisibleAsExpected() {
    hideWindow();
    driver.requireNotVisible(window);
  }

  public void shouldFailIfComponentIsVisibleAndExpectedToBeNotVisible() {
    try {
      driver.requireNotVisible(button);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'visible'")
                                .contains("expected:<false> but was:<true>");
    }
  }

  public void shouldPassIfComponentIsEnabledAsExpected() {
    driver.requireEnabled(button);
  }

  public void shouldFailIfComponentIsNotEnabledAndExpectedToBeEnabled() {
    disableButton();
    try {
      driver.requireEnabled(button);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'enabled'")
                                .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfComponentIsEnabledAsExpectedBeforeTimeout() {
    driver.requireEnabled(button, timeout(100));
  }

  public void shouldFailIfComponentIsNotEnabledAsExpectedBeforeTimeout() {
    disableButton();
    int timeout = 1000;
    StopWatch stopWatch = startNewStopWatch();
    try {
      driver.requireEnabled(button, timeout(timeout));
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for")
                                .contains(button.getClass().getName())
                                .contains("to be enabled");
    }
    stopWatch.stop();
    assertThatWaited(stopWatch, timeout);
  }

  private void assertThatWaited(StopWatch stopWatch, long minimumWaitedTime) {
    long ellapsedTimeInMs = stopWatch.ellapsedTime();
    assertThat(ellapsedTimeInMs).isGreaterThanOrEqualTo(minimumWaitedTime);
  }

  public void shouldPassIfComponentIsNotEnabledAsExpected() {
    disableButton();
    driver.requireDisabled(button);
  }

  public void shouldFailIfComponentIsEnabledAndExpectedToBeNotEnabled() {
    try {
      driver.requireDisabled(button);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'enabled'")
                                .contains("expected:<false> but was:<true>");
    }
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfArrayOfKeysToPressAndReleaseIsNull() {
    int[] keyCodes = null;
    driver.pressAndReleaseKeys(button, keyCodes);
  }

  public void shouldPressAndReleaseKeys() {
    assertThatTextFieldIsEmpty();
    int[] keyCodes = { VK_A, VK_C, VK_E };
    driver.pressAndReleaseKeys(textField, keyCodes);
    assertThat(textOf(textField)).isEqualTo("ace");
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeysInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver.pressAndReleaseKeys(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeysInNotVisibleComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver.pressAndReleaseKeys(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfKeyPressInfoIsNull() {
    driver.pressAndReleaseKey(button, null);
  }

  public void shouldPressAndReleaseKeyInSpecifiedKeyPressInfo() {
    assertThatTextFieldIsEmpty();
    driver.pressAndReleaseKey(textField, keyCode(VK_A).modifiers(SHIFT_MASK));
    assertThat(textOf(textField)).isEqualTo("A");
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeyInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver.pressAndReleaseKey(textField, keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeyInNotShowingComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver.pressAndReleaseKey(textField, keyCode(VK_A).modifiers(SHIFT_MASK));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldPressKeyAndReleaseKey() {
    assertThatTextFieldIsEmpty();
    int key = VK_A;
    driver.pressKey(textField, key);
    // test both pressKey and releaseKey
    driver.releaseKey(textField, key);
    assertThat(textOf(textField)).isEqualTo("a");
  }

  public void shouldThrowErrorWhenPressingKeyInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver.pressKey(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenPressingKeyInNotShowingComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver.pressKey(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenReleasingKeyInDisabledComponent() {
    assertThatTextFieldIsEmpty();
    disableTextField();
    try {
      driver.releaseKey(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldThrowErrorWhenReleasingKeyInNotShowingComponent() {
    assertThatTextFieldIsEmpty();
    hideWindow();
    try {
      driver.releaseKey(textField, VK_A);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThatTextFieldIsEmpty();
  }

  public void shouldGiveFocusToComponent() {
    driver.focus(button);
    pause(new Condition("Component has focus") {
      public boolean test() {
        return hasFocus(button);
      }
    });
  }
  
  public void shouldFailIfComponentDoesNotHaveFocus() {
    try {
      driver.requireFocused(button);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("Expected component")
                                .contains("to have input focus");
    }
  }
  
  public void shouldPassIfComponentHasFocus() {
    driver.requireFocused(textField);
  }

  public void shouldThrowErrorWhenGivingFocusToDisabledComponent() {
    disableButton();
    try {
      driver.focus(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(hasFocus(button)).isFalse();
  }

  public void shouldThrowErrorWhenGivingFocusToNotShowingComponent() {
    hideWindow();
    try {
      driver.focus(button);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(hasFocus(button)).isFalse();
  }

  private void disableButton() {
    disable(button);
    robot.waitForIdle();
  }

  private void disableTextField() {
    disable(textField);
  }

  private void hideWindow() {
    setVisible(window, false);
    robot.waitForIdle();
  }

  private void assertThatTextFieldIsEmpty() {
    assertThat(textOf(textField)).isEmpty();
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

    final JTextField textField = new JTextField(20);
    final MyButton button = new MyButton("Click Me");

    private MyWindow() {
      super(ComponentDriverTest.class);
      addComponents(textField, button);
    }
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean waitToRequestFocus;

    MyButton(String text) {
      super(text);
    }

    void waitToRequestFocus() { waitToRequestFocus = true; }

    @Override public boolean requestFocusInWindow() {
      if (waitToRequestFocus) pause(PAUSE_TIME);
      return super.requestFocusInWindow();
    }
  }
}
