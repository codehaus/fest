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

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static javax.swing.JSplitPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithCurrentAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

/**
 * Tests for <code>{@link JSplitPaneDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSplitPaneDriverTest {

  private Robot robot;
  private JSplitPane splitPane;
  private JSplitPaneDriver driver;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
    driver = new JSplitPaneDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "orientationsAndModes")
  public void shouldMoveDividerToGivenLocation(int orientation) {
    MyWindow window = MyWindow.createNew(orientation);
    splitPane = window.splitPane;
    robot.showWindow(window);
    int newLocation = splitPane.getDividerLocation() + 100;
    driver.moveDividerTo(splitPane, newLocation);
    assertThat(splitPane.getDividerLocation()).isEqualTo(newLocation);
  }

  @DataProvider(name = "orientationsAndModes") public Object[][] orientationsAndModes() {
    return new Object[][] {
        { VERTICAL_SPLIT }, { HORIZONTAL_SPLIT }
    };
  }

  @Test(groups = GUI, dataProvider = "orientations")
  public void shouldThrowErrorWhenMovingDividerInDisabledJSplitPane(int orientation) {
    MyWindow window = MyWindow.createNew(orientation);
    splitPane = window.splitPane;
    robot.showWindow(window);
    disable(splitPane);
    robot.waitForIdle();
    try {
      driver.moveDividerTo(splitPane, 100);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "orientations")
  public void shouldThrowErrorWhenMovingDividerInNotShowingJSplitPane(int orientation) {
    MyWindow window = MyWindow.createNew(orientation);
    splitPane = window.splitPane;
    try {
      driver.moveDividerTo(splitPane, 100);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @DataProvider(name = "orientations") public Object[][] orientations() {
    return new Object[][] {
        { VERTICAL_SPLIT }, { HORIZONTAL_SPLIT }
    };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSplitPane splitPane;

    @RunsInEDT
    static MyWindow createNew(final int orientation) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(orientation);
        }
      });
    }

    private MyWindow(int orientation) {
      super(JSplitPaneDriverTest.class);
      splitPane = new JSplitPane(orientation, new JList(), new JList());
      splitPane.setDividerLocation(150);
      splitPane.setPreferredSize(new Dimension(300, 300));
      add(splitPane);
    }
  }
}
