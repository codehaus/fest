/*
 * Created on Aug 10, 2008
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTableHasSelectionQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTableHasSelectionQueryTest {

  private Robot robot;
  private TestTable table;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnFalseIfTableHasNoSelection() {
    clearSelectionOf(table);
    robot.waitForIdle();
    assertThat(hasSelection(table)).isFalse();
  }

  public void shouldReturnTrueIfTableHasSelection() {
    selectAllIn(table);
    robot.waitForIdle();
    assertThat(hasSelection(table)).isTrue();
  }

  @RunsInEDT
  private static void selectAllIn(final TestTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.selectAll();
      }
    });
  }
  
  @RunsInEDT
  private static boolean hasSelection(final TestTable table) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return JTableHasSelectionQuery.hasSelection(table);
      }
    });
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

    final TestTable table = new TestTable(2, 4);

    private MyWindow() {
      super(JTableHasSelectionQueryTest.class);
      addComponents(table);
    }
  }
}
