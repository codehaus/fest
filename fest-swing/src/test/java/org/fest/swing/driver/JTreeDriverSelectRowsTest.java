/*
 * Created on Jul 16, 2009
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

import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeDriver#selectRows(javax.swing.JTree, int[])}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverSelectRowsTest extends JTreeDriverSelectCellTestCase {

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRowArrayIsNull() {
    int[] rows = null;
    driver().selectRows(tree(), rows);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfRowArrayIsEmpty() {
    int[] rows = new int[0];
    driver().selectRows(tree(), rows);
  }

  public void shouldSelectNodesByRow() {
    clearTreeSelection();
    updateTreeWithDefaultSelectionModel();
    requireNoSelection();
    int[] rows = { 0, 1, 2 };
    driver().selectRows(tree(), rows);
    requireSelectedRows(rows);
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfAnyRowToSelectIsOutOfBounds(int invalidRow) {
    driver().selectRows(tree(), new int[] { invalidRow });
  }

  public void shouldThrowErrorWhenSelectingNodesByRowInDisabledJTree() {
    disableTree();
    int[] rows = { 0, 1, 2 };
    try {
      driver().selectRows(tree(), rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodesByRowInNotShowingJTree() {
    hideWindow();
    int[] rows = { 0, 1, 2 };
    try {
      driver().selectRows(tree(), rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
