/*
 * Created on Aug 9, 2008
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link WindowMoveToFrontTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class WindowMoveToFrontTaskTest {

  private Robot robot;
  private MyWindow windowOne;
  private MyWindow windowTwo;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    windowOne = MyWindow.createNew();
    windowTwo = MyWindow.createNew();
    robot.showWindow(windowOne);
    robot.showWindow(windowTwo);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldMoveWindowToFront() {
    assertThat(hasFocus(windowTwo)).isTrue();
    WindowMoveToFrontTask.toFront(windowOne);
    robot.waitForIdle();
    assertThat(hasFocus(windowOne)).isTrue();
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

    private static int counter;

    private MyWindow() {
      super(WindowMoveToFrontTaskTest.class);
      setTitle(concat(getTitle(), " - ", valueOf(++counter)));
      setPreferredSize(new Dimension(500, 300));
    }
  }
}
