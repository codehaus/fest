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

import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.awt.FluentDimension;
import org.fest.swing.test.awt.FluentPoint;
import org.fest.swing.test.swing.TestWindow;

import static java.awt.Frame.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;

/**
 * Tests for <code>{@link FrameDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FrameDriverTest {

  private Robot robot;
  private TestWindow window;
  private FrameDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.createNewWindow(getClass());
    driver = new FrameDriver(robot);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIconifyAndDeiconifyFrame() {
    driver.iconify(window);
    assertThat(frameState()).isEqualTo(ICONIFIED);
    driver.deiconify(window);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  public void shouldThrowErrorWhenIconifyingDisabledFrame() {
    disableWindow();
    try {
      driver.iconify(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenIconifyingNotShowingFrame() {
    hideWindow();
    try {
      driver.iconify(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenDeiconifyingDisabledFrame() {
    driver.iconify(window);
    disableWindow();
    try {
      driver.deiconify(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenDeiconifyingNotShowingFrame() {
    driver.iconify(window);
    hideWindow();
    try {
      driver.deiconify(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldMaximizeFrame() {
    driver.maximize(window);
    int frameState = frameState() & MAXIMIZED_BOTH;
    assertThat(frameState).isEqualTo(MAXIMIZED_BOTH);
  }

  public void shouldThrowErrorWhenMaximizingDisabledFrame() {
    disableWindow();
    try {
      driver.maximize(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenMaximizingNotShowingFrame() {
    hideWindow();
    try {
      driver.maximize(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldNormalizeFrame() {
    driver.maximize(window);
    driver.normalize(window);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  private int frameState() {
    return window.getExtendedState();
  }

  public void shouldThrowErrorWhenNormalizingDisabledFrame() {
    driver.maximize(window);
    disableWindow();
    try {
      driver.normalize(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenNormalizingNotShowingFrame() {
    driver.maximize(window);
    hideWindow();
    try {
      driver.normalize(window);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldResizeFrameToGivenSize() {
    FluentDimension newSize = frameSize().addToWidth(20).addToHeight(40);
    driver.resizeTo(window, newSize);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldThrowErrorWhenResizingDisabledFrame() {
    disableWindow();
    try {
      driver.resize(window, 10, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingNotShowingFrame() {
    hideWindow();
    try {
      driver.resize(window, 10, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resize(window, 10, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  public void shouldResizeFrameToGivenWidth() {
    FluentDimension newSize = frameSize().addToWidth(20);
    driver.resizeWidthTo(window, newSize.width);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldThrowErrorWhenResizingWidthOfDisabledFrame() {
    disableWindow();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingWidthOfNotShowingFrame() {
    hideWindow();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingWidthOfNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  public void shouldResizeFrameToGivenHeight() {
    FluentDimension newSize = frameSize().addToHeight(30);
    driver.resizeHeightTo(window, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  private final FluentDimension frameSize() {
    return new FluentDimension(sizeOf(window));
  }

  public void shouldThrowErrorWhenResizingHeightOfDisabledFrame() {
    disableWindow();
    try {
      driver.resizeHeightTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingHeightOfNotShowingFrame() {
    hideWindow();
    try {
      driver.resizeHeightTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingHeightOfNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resizeHeightTo(window, 10);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotResizableComponent(e);
    }
  }

  private static void makeNotResizable(final TestWindow window) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.setResizable(false);
      }
    });
  }

  public void shouldMoveFrame() {
    Point p = locationOnScreenOfWindow().addToX(10).addToY(10);
    driver.moveTo(window, p);
    assertThat(locationOnScreenOfWindow()).isEqualTo(p);
  }

  private FluentPoint locationOnScreenOfWindow() {
    return new FluentPoint(locationOnScreen(window));
  }

  public void shouldThrowErrorWhenMovingDisabledFrame() {
    disableWindow();
    try {
      driver.moveTo(window, new Point(6, 8));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  private void disableWindow() {
    disable(window);
    robot.waitForIdle();
  }

  public void shouldThrowErrorWhenMovingNotShowingFrame() {
    hideWindow();
    try {
      driver.moveTo(window, new Point(6, 8));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }
}
