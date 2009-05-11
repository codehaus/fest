/*
 * Created on Jun 10, 2008
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

import org.testng.annotations.Test;

import org.fest.swing.cell.JTableCellWriter;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JTableTextComponentEditorCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTableTextComponentEditorCellWriterTest extends JTableCellWriterTestCase {

  protected JTableCellWriter createWriter() {
    return new JTableTextComponentEditorCellWriter(robot());
  }

  public void shouldEnterTextInTextComponentEditor() {
    writer().enterValue(table(), 4, 3, "8");
    assertThat(valueAt(4, 3)).isEqualTo(8);
  }

  public void shouldCancelEditingIfTextComponentVisible() {
    int row = 0;
    int column = 3;
    assertThat(valueAt(row, column)).isEqualTo(5);
    writer().startCellEditing(table(), row, column);
    writer().cancelCellEditing(table(), row, column);
    assertThat(valueAt(row, column)).isEqualTo(5);
  }
}
