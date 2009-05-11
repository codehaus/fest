/*
 * Created on Feb 2, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.util.Strings.concat;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=108">Bug 108</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class Bug108_FindContainerShowingOnlyTest {

  private MyWindow window;
  private Robot robot;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window, new Dimension(400, 300));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindOnlyVisibleContainer() {
    hide(window.hiddenInternalFrame);
    robot.waitForIdle();
    JInternalFrameFixture fixture = new JInternalFrameFixture(robot, "target");
    assertThat(fixture.target).isSameAs(window.visibleInternalFrame);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private final JDesktopPane desktop = new JDesktopPane();
    private final MyInternalFrame hiddenInternalFrame = new MyInternalFrame();
    private final MyInternalFrame visibleInternalFrame = new MyInternalFrame();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
 
    private MyWindow() {
      super(Bug108_FindContainerShowingOnlyTest.class);
      setContentPane(desktop);
      addToDesktop(hiddenInternalFrame);
      addToDesktop(visibleInternalFrame);
    }

    private void addToDesktop(MyInternalFrame frame) {
      frame.setVisible(true);
      desktop.add(frame);
      frame.toFront();
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private static int index;

    MyInternalFrame() {
      super(concat("Internal Frame ", String.valueOf(index++)), true, true, true, true);
      setName("target");
      setSize(200, 100);
    }
  }
}
