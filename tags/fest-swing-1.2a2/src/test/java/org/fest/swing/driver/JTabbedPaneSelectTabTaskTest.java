/*
 * Created on Aug 11, 2008
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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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

/**
 * Tests for <code>{@link JTabbedPaneSelectTabTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTabbedPaneSelectTabTaskTest {

  private Robot robot;
  private JTabbedPane tabbedPane;
  private int index;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tabbedPane = window.tabbedPane;
    robot.showWindow(window);
    index = 1;
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetSelectedTabWithIndexInJTabbedPane() {
    assertThat(selectedTabIndex()).isNotEqualTo(index);
    JTabbedPaneSelectTabTask.setSelectedTab(tabbedPane, index);
    robot.waitForIdle();
    assertThat(selectedTabIndex()).isEqualTo(index);
  }

  @RunsInEDT
  private int selectedTabIndex() {
    return selectedIndexOf(tabbedPane);
  }

  @RunsInEDT
  private static int selectedIndexOf(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tabbedPane.getSelectedIndex();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTabbedPane tabbedPane = new JTabbedPane();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTabbedPaneSelectTabTaskTest.class);
      tabbedPane.addTab("One", new JPanel());
      tabbedPane.addTab("Two", new JPanel());
      add(tabbedPane);
      setPreferredSize(new Dimension(300, 200));
    }
  }
}
