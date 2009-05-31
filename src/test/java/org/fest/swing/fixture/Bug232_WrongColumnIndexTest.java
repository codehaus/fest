/*
 * Created on Nov 30, 2008
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

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.data.TableCellByColumnId;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=232" target="_blank">issue 232</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups =  { GUI, BUG })
public class Bug232_WrongColumnIndexTest {
  
  private Robot robot;
  private MyWindow window;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldEnterValueAfterRemovingColumn() {
    removeFirstColumn(window.table);
    robot.waitForIdle();
    JTableFixture table = new JTableFixture(robot, window.table);
    table.cell(TableCellByColumnId.row(0).columnId("2")).enterValue("foo");
  }
  
  private static void removeFirstColumn(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));
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

    final JTable table = new JTable(2, 2);
    
    private MyWindow() {
      super(Bug232_WrongColumnIndexTest.class);
      addComponents(table);
      TableColumnModel columnModel = table.getColumnModel();
      columnModel.getColumn(0).setIdentifier("1");
      columnModel.getColumn(1).setIdentifier("2");
    }
  }
}
