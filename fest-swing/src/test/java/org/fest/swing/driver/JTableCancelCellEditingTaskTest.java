/*
 * Created on May 22, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.core.TestGroups.ACTION;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableCancelCellEditingTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTableCancelCellEditingTaskTest extends JTableCellEditingTaskTestCase {

  public void shouldThrowErrorIfRowIndexIsOutOfBounds() {
    try {
      JTableCancelCellEditingTask.cancelEditing(table(), 8, 2);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("row '8' should be between <0> and <4>");
    }
  }

  public void shouldThrowErrorIfColumnIndexIsOutOfBounds() {
    try {
      JTableCancelCellEditingTask.cancelEditing(table(), 0, 8);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("column '8' should be between <0> and <1>");
    }
  }

  public void shouldThrowErrorIfCellIsNotEditable() {
    try {
      JTableCancelCellEditingTask.cancelEditing(table(), 0, 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Expecting the cell [0,0] to be editable");
    }
  }

  public void shouldCancelCellEditing() {
    int row = 0;
    int col = 1;
    editTableCellAt(row, col);
    JTableCancelCellEditingTask.cancelEditing(table(), row, col);
    robot().waitForIdle();
    assertThat(isTableEditing()).isFalse();
    MyCellEditor cellEditor = table().cellEditor();
    assertThat(cellEditor.cellEditingCanceled()).isTrue();
    assertThat(cellEditor.cellEditingStopped()).isFalse();
  }

  public void shouldNotThrowErrorIfCellEditorIsNull() {
    JTableCancelCellEditingTask.cancelEditing(null);
  }

}
