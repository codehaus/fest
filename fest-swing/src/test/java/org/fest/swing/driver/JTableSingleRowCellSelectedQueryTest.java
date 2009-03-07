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

import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.JTableSelectCellsTask.selectCells;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTableSingleRowCellSelectedQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTableSingleRowCellSelectedQueryTest {

  private Robot robot;
  private JTable table;
  
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
  
  public void shouldReturnCellIsSelectedIfOneRowAndOneColumnAreSelectedOnly() {
    TableCell cell = row(0).column(2);
    selectCells(table, cell, cell);
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isTrue();
  }
  
  public void shouldReturnCellIsNotSelectedIfCellIsNotSelected() {
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }

  public void shouldReturnCellIsNotSelectedIfRowIsSelectedAndColumnIsNot() {
    selectRow(table, 0);
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }
  
  @RunsInEDT
  private static void selectRow(final JTable table, final int row) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.setRowSelectionInterval(row, row);
      }
    });
  }

  public void shouldReturnCellIsNotSelectedIfRowAndColumnAreSelectedAndMultipleRowsAreSelected() {
    selectCells(table, row(0).column(2), row(0).column(4));
    robot.waitForIdle();
    assertThat(isCellSelected(table, 0, 2)).isFalse();
  }

  @RunsInEDT
  private static boolean isCellSelected(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return JTableSingleRowCellSelectedQuery.isCellSelected(table, row, column);
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTable table = new TestTable(3, 6);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableSingleRowCellSelectedQueryTest.class);
      addComponents(table);
    }
  }
}
