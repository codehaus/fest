/*
 * Created on Jul 10, 2009
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

import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-122"
 * target="_blank">FEST-122</a>.
 *
 * @author Alex Ruiz 
 */
@Test(groups = GUI)
public class FEST122_AddSupportForEditableJComboBoxAsCellEditor {

  private FrameFixture frame;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    frame = new FrameFixture(MyWindow.createNew());
    frame.show();
  }
  
  @AfterMethod public void tearDown() {
    frame.cleanUp();
  }
  
  public void shouldEnterValueInCellWithEditableComboBoxAsEditor() {
    JTableCellFixture cell = frame.table("data").cell(row(0).column(0));
    cell.enterValue("Pink");
    cell.requireValue("Pink");
    cell.enterValue("Blue");
    cell.requireValue("Blue");
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
    
    final JTable table = data();
    
    private MyWindow() {
      super(FEST122_AddSupportForEditableJComboBoxAsCellEditor.class);
      table.setName("data");
      add(new JScrollPane(table));
    }
  
    private static JTable data() {
      JTable table = new JTable(8, 6);
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
      setUpColorColumn(table, table.getColumnModel().getColumn(0));
      return table;
    }

    private static void setUpColorColumn(JTable table, TableColumn column) {
      // Set up the editor for the sport cells.
      JComboBox comboBox = new JComboBox();
      comboBox.setEditable(true);
      comboBox.addItem("Blue");
      comboBox.addItem("Red");
      comboBox.addItem("Yellow");
      column.setCellEditor(new DefaultCellEditor(comboBox));
      DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
      renderer.setToolTipText("Click for combo box");
      column.setCellRenderer(renderer);
    }
  }
}
