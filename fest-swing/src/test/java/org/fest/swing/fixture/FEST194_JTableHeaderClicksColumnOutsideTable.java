/*
 * Created on Jul 22, 2009
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

import static javax.swing.JTable.AUTO_RESIZE_OFF;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.BUG;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

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
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-194" target="_blank">FEST-194</a>
 * 
 * @author Andriy Tsykholyas
 * @author Alex Ruiz
 */
@Test(groups = { BUG, GUI })
public class FEST194_JTableHeaderClicksColumnOutsideTable {

  private Robot robot;
  private HeaderMouseListener headerMouseListener;
  private JTableFixture table;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = BasicRobot.robotWithNewAwtHierarchy();
    headerMouseListener = new HeaderMouseListener();
    MyWindow window = MyWindow.createNew();
    window.table.getTableHeader().addMouseListener(headerMouseListener);
    table = new JTableFixture(robot, window.table);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void testTableHeaderClick() {
    int columnCount = columnCountOf(table.component());
    for (int column = 0; column < columnCount; column++) {
      table.tableHeader().clickColumn(column);
      assertThat(headerMouseListener.headerClickCounter).isEqualTo(column + 1);
    }
  }
  
  @RunsInEDT
  static int columnCountOf(final JTable table) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return table.getColumnCount();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final int COLUMN_WIDTH = 200;
    private static final Dimension FRAME_SIZE = new Dimension((int)(COLUMN_WIDTH * 1.3), 200);

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    final JTable table = table();
    
    private MyWindow() {
      super(FEST194_JTableHeaderClicksColumnOutsideTable.class);
      setLayout(new GridLayout(1, 0));
      setMinimumSize(FRAME_SIZE);
      setMaximumSize(FRAME_SIZE);
      setPreferredSize(FRAME_SIZE);
      addComponents(new JScrollPane(table));
    }

    private static JTable table() {
      JTable table = new JTable(tableData(), array("Visible", "Semi Visible", "Not Visible"));
      table.setAutoResizeMode(AUTO_RESIZE_OFF);
      for (int col = 0; col < table.getColumnCount(); col++)
        configure(table.getColumnModel().getColumn(col));
      return table;
    }

    private static Object[][] tableData() {
      return new Object[][] { 
          { "data", "data", "data" }, 
          { "data", "data", "data" }, 
          { "data", "data", "data" } 
      };
    }

    private static void configure(TableColumn column) {
      column.setPreferredWidth(COLUMN_WIDTH);
      column.setMaxWidth(COLUMN_WIDTH);
      column.setMinWidth(COLUMN_WIDTH);
    }
  }

  private static class HeaderMouseListener extends MouseAdapter {
    int headerClickCounter;
    
    HeaderMouseListener() {}

    @Override public void mouseClicked(MouseEvent e) {
      headerClickCounter++;
    }
  }

}
