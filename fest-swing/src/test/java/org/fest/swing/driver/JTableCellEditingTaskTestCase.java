/*
 * Created on May 23, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.*;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Understands a base test case for tasks related to editing <code>{@link JTable}</code> cells.
 *
 * @author Alex Ruiz
 */
public abstract class JTableCellEditingTaskTestCase {

  private Robot robot;
  private MyWindow window;

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew(getClass());
    robot.showWindow(window);
  }

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  final void editTableCellAt(final int row, final int col) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table().editCellAt(row, col);
      }
    });
    robot.waitForIdle();
    assertThat(isTableEditing()).isTrue();
  }

  final boolean isTableEditing() {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return window.table.isEditing();
      }
    });
  }

  final Robot robot() { return robot; }

  final MyTable table() { return window.table; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTable table = new MyTable();

    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(new JScrollPane(table));
    }
  }

  static class MyTable extends JTable {
    private static final long serialVersionUID = 1L;

    private final MyCellEditor cellEditor = new MyCellEditor();

    MyTable() {
      super(new MyTableModel());
      setDefaultEditor(String.class, cellEditor);
      setPreferredScrollableViewportSize(new Dimension(200, 70));
    }

    MyCellEditor cellEditor() { return cellEditor; }
  }

  static class MyCellEditor extends DefaultCellEditor {
    private static final long serialVersionUID = 1L;

    private boolean cellEditingCanceled;
    private boolean cellEditingStopped;

    MyCellEditor() {
      super(new JTextField());
    }

    @Override public void cancelCellEditing() {
      cellEditingCanceled = true;
      super.cancelCellEditing();
    }

    @Override public boolean stopCellEditing() {
      cellEditingStopped = true;
      return super.stopCellEditing();
    }

    boolean cellEditingCanceled() { return cellEditingCanceled; }

    boolean cellEditingStopped() { return cellEditingStopped; }
  }

  static class MyTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final String[] columnNames = { "First Name", "Sport" };
    private final Object[][] data = {
        { "Mary",   "Snowboarding" },
        { "Alison", "Rowing" },
        { "Kathy",  "Knitting" },
        { "Sharon", "Speed reading" },
        { "Philip", "Pool" }
    };

    public int getColumnCount() {
      return columnNames.length;
    }

    public int getRowCount() {
      return data.length;
    }

    @Override public Class<?> getColumnClass(int columnIndex) {
      return getValueAt(0, columnIndex).getClass();
    }

    @Override public String getColumnName(int col) {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
      return data[row][col];
    }

    @Override public boolean isCellEditable(int row, int col) {
      if (col < 1) return false;
      return true;
    }

    @Override public void setValueAt(Object value, int row, int col) {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }
}