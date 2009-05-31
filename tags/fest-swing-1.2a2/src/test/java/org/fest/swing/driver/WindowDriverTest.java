/*
 * Created on Apr 5, 2008
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
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.awt.FluentDimension;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetVisibleTask.setVisible;

/**
 * Tests for <code>{@link WindowDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class WindowDriverTest {

  private Robot robot;
  private Frame window;
  private WindowDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.createNewWindow(getClass());
    driver = new WindowDriver(robot);
    robot.showWindow(window, new Dimension(100, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldResizeWindow() {
    Dimension newSize = new FluentDimension(sizeOf(window)).addToHeight(100).addToWidth(200);
    driver.resize(window, newSize.width, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldResizeWidthOnly() {
    Dimension newSize = new FluentDimension(sizeOf(window)).addToWidth(200);
    driver.resizeWidthTo(window, newSize.width);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldResizeHeightOnly() {
    Dimension newSize = new FluentDimension(sizeOf(window)).addToHeight(100);
    driver.resizeHeightTo(window, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldMoveWindow() {
    Point newPosition = new Point(200, 200);
    driver.moveTo(window, newPosition);
    assertThat(locationOnScreen(window)).isEqualTo(newPosition);
  }

  public void shouldMoveWindowToFront() {
    TestWindow window2 = TestWindow.createNewWindow(getClass());
    robot.showWindow(window2, new Dimension(100, 100));
    assertThat(isActive(window)).isFalse();
    driver.moveToFront(window);
    assertThat(isActive(window)).isTrue();
  }
  
  public void shouldMoveWindowToBack() {
    TestWindow window2 = TestWindow.createNewWindow(getClass());
    robot.showWindow(window2, new Dimension(50, 50));
    assertThat(isActive(window2)).isTrue();
    driver.moveToBack(window2);
    assertThat(isActive(window2)).isFalse();
  }

  @RunsInEDT
  private static boolean isActive(final Window w) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return w.isActive();
      }
    });
  }
  
  public void shouldCloseWindow() {
    driver.close(window);
    robot.waitForIdle();
    assertThat(isVisible(window)).isFalse();
  }

  public void shouldShowWindow() {
    setVisible(window, false);
    robot.waitForIdle();
    driver.show(window);
    assertThat(isVisible(window)).isTrue();
  }

  public void shouldShowWindowUsingGivenSize() {
    setVisible(window, false);
    robot.waitForIdle();
    Dimension newSize = new Dimension(600, 300);
    driver.show(window, newSize);
    assertThat(isVisible(window)).isTrue();
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }
}
