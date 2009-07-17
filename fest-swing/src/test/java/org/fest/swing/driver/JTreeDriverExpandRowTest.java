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
 * Tests for <code>{@link JTreeDriver#expandRow(javax.swing.JTree, int)}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeDriverExpandRowTest extends JTreeDriverToggleCellTestCase {

  public void shouldExpandNodeByRow() {
    int row = 5;
    requireRowCollapsed(row);
    driver().expandRow(tree(), row);
    requireRowExpanded(row);
  }

  public void shouldNotDoAnythingIfRowAlreadyExpanded() {
    int row = 0;
    requireRowExpanded(row);
    driver().expandRow(tree(), row);
    requireRowExpanded(row);
  }

  @Test(groups = GUI, dataProvider = "outOfBoundRowIndices", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowToExpandIsOutOfBounds(int invalidRow) {
    driver().expandRow(tree(), invalidRow);
  }

  public void shouldThrowErrorWhenExpandingRowInDisabledJTree() {
    disableTree();
    try {
      driver().expandRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenExpandingRowInNotShowingJTree() {
    hideWindow();
    try {
      driver().expandRow(tree(), 0);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
}
