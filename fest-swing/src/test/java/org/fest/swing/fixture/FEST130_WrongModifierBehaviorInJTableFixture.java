/*
 * Created on Jul 9, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.fixture;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.BUG;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-130"
 * target="_blank">FEST-130</a>.
 * 
 * @author neals01
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class FEST130_WrongModifierBehaviorInJTableFixture {

  private Robot robot;
  private FrameFixture frame;
  private JTableFixture table;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
    table = new JTableFixture(robot, "Table");
  }

  @AfterMethod public void tearDown() {
      robot.cleanUp();
  }

  public void shouldUseControlModifierCorrectly() {
    table.cell(row(1).column(0)).click();
    assertThatSelectedRowsAre(1);
    robot.pressModifiers(CTRL_MASK);
    table.cell(row(3).column(0)).click();
    table.cell(row(7).column(0)).click();
    robot.releaseModifiers(CTRL_MASK);
    assertThatSelectedRowsAre(1, 3, 7);
  }

  public void shouldUseShiftModifierCorrectly() {
    table.cell(row(1).column(0)).click();
    assertThatSelectedRowsAre(1);
    robot.pressModifiers(SHIFT_MASK);
    table.cell(row(4).column(0)).click();
    robot.releaseModifiers(SHIFT_MASK);
    assertThatSelectedRowsAre(1, 2, 3, 4);
  }

  private void assertThatSelectedRowsAre(int...rows) {
    int[] selectedRows = selectedRowsInTable();
    assertThat(selectedRows.length).isEqualTo(rows.length);
    for (int i = 0; i < rows.length; i++)
      assertThat(selectedRows[i]).isEqualTo(rows[i]);
  }
  
  private int[] selectedRowsInTable() {
    return selectedRowsIn(table.component());
  }

  @RunsInEDT
  private static int[] selectedRowsIn(final JTable table) {
    return execute(new GuiQuery<int[]>() {
      protected int[] executeInEDT() throws Throwable {
        return table.getSelectedRows();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    final JTable table = new JTable();
    
    private MyWindow() {
      super(FEST130_WrongModifierBehaviorInJTableFixture.class);
      table.setName("Table");
      List<String> values = new ArrayList<String>();
      for (int i = 0; i < 15; i++) values.add("AB" + i);
      table.setModel(new MyTableModel(values));
      addComponents(table);
    }
  }
  
  private static class MyTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private final List<String> values;

    MyTableModel(List<String> values) {
      this.values = values;
    }
    
    @Override public String getColumnName(int col) {
      return "Col" + col;
    }

    public int getColumnCount() {
      return 3;
    }

    public Object getValueAt(int row, int col) {
      String val = values.get(row);
      if (val == null) return "";
      return Character.toString(val.charAt(col));
    }

    @Override public boolean isCellEditable(int row, int col) {
      return false;
    }

    @Override public int getRowCount() {
      return values.size();
    }
  }
}
