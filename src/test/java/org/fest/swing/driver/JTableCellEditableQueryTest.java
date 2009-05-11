/*
 * Created on Aug 6, 2008
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

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TableRenderDemo;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;

/**
 * Tests for <code>{@link JTableCellEditableQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTableCellEditableQueryTest {

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

  @Test(dataProvider = "cells", groups = { GUI, ACTION })
  public void shouldIndicateWhetherCellIsEditableOrNot(int column, boolean editable) {
    // TODO test validation of cell indices
    assertThat(isCellEditable(table, 0, column)).isEqualTo(editable);
  }

  @RunsInEDT
  private static boolean isCellEditable(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return JTableCellEditableQuery.isCellEditable(table, row(row).column(column));
      }
    });
  }
  
  @DataProvider(name = "cells") public Object[][] cells() {
    return new Object[][] {
        { 0, false },  
        { 1, false },  
        { 2, true },  
        { 3, true },  
        { 4, true },  
    };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTable table;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JTableCellEditableQueryTest.class);
      TableRenderDemo content = new TableRenderDemo();
      table = content.table;
      setContentPane(content);
    }
  }
}
