/*
 * Created on Nov 20, 2008
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

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.data.TableCell;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands an action, executed in the event dispatch thread, that returns the first cell in a 
 * <code>{@link JTable}</code> whose value matches the given one.
 *
 * @author Alex Ruiz
 */
final class JTableMatchingCellQuery {

  @RunsInEDT
  static TableCell cellWithValue(final JTable table, final String value, final JTableCellReader cellReader) {
    return execute(new GuiQuery<TableCell>() {
      protected TableCell executeInEDT() {
        return findMatchingCell(table, value, cellReader);
      }
    });
  }

  @RunsInCurrentThread
  private static TableCell findMatchingCell(JTable table, String value, JTableCellReader cellReader) {
    int rCount = table.getRowCount();
    int cCount = table.getColumnCount();
    for (int r = 0; r < rCount; r++)
      for (int c = 0; c < cCount; c++)
        if (cellHasValue(table, r, c, value, cellReader)) return row(r).column(c);
    throw actionFailure(concat("Unable to find cell with value ", quote(value)));
  }

  @RunsInCurrentThread
  private static boolean cellHasValue(JTable table, int row, int column, String value, 
      JTableCellReader cellReader) {
    return areEqual(value, cellReader.valueAt(table, row, column));
  }
  
  private JTableMatchingCellQuery() {}
}
