/*
 * Created on Jul 11, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.testng.annotations.*;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-43"
 * target="_blank">FEST-43</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FEST43_AddSelectedRowToTableCell {

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

  public void shouldGetCellWithSelectedRow() {
    JTableFixture table = frame.table("data");
    table.selectCell(row(1).column(1));
    JTableCellFixture cell = table.cell(row(table.selectedRow()).column(0));
    assertThat(cell.row()).isEqualTo(1);
    assertThat(cell.column()).isEqualTo(0);
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

    final JTable table = new JTable(8, 6);

    private MyWindow() {
      super(FEST43_AddSelectedRowToTableCell.class);
      table.setName("data");
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
      add(new JScrollPane(table));
    }
  }
}
