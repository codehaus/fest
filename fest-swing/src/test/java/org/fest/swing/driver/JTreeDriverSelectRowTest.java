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
 * Tests for <code>{@link JTreeDriver#selectRow(javax.swing.JTree, int)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverSelectRowTest extends JTreeDriverSelectCellTestCase {

  public void shouldSelectNodeByRow() {
    clearTreeSelection();
    driver().selectRow(tree(), 0);
    requireSelectedRows(0);
    driver().selectRow(tree(), 1);
    requireSelectedRows(1);
    driver().selectRow(tree(), 0);
    requireSelectedRows(0);
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowToSelectIsOutOfBounds(int invalidRow) {
    driver().selectRow(tree(), invalidRow);
  }

  public void shouldThrowErrorWhenSelectingNodeByRowInDisabledJTree() {
    disableTree();
    try {
      driver().selectRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNodeByRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().selectRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

}
